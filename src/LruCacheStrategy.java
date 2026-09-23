import java.util.HashMap;
import java.util.Map;

public class LruCacheStrategy<K, V> implements CacheStrategy<K, V> {

    private Map<K, LruNode<K,V>> lruNodeMap;
    private LruNode<K, V> head;
    private LruNode<K, V> tail;
    private int currentCapacity;

    public LruCacheStrategy(int currentCapacity) {

        if(currentCapacity <= 0) throw new RuntimeException("capacity should be > 0");

        this.lruNodeMap = new HashMap<>();
        this.head = new LruNode<>(null, null, -1, -1);
        this.tail = new LruNode<>(null, null, -1, -1);
        this.head.next = tail;
        this.tail.prev = head;
        this.currentCapacity = currentCapacity;
    }


    public synchronized V get(K key) {

        LruNode<K, V> lruNode = lruNodeMap.get(key);

        if (lruNode == null) return null;

        else if ((System.currentTimeMillis() - lruNode.timestamp) / 1000 >= lruNode.ttl) {
            deleteLruNode(lruNode);
            lruNodeMap.remove(key);
            currentCapacity++;
            return null;
        }

        else {
            deleteLruNode(lruNode);
            insertBeforeHead(lruNode);
            V value = lruNode.value;
            return value;
        }

    }


    public synchronized void put(K key, V value, long ttl) {

        long timestamp = System.currentTimeMillis();

        if (lruNodeMap.containsKey(key)) {
            LruNode<K, V> lruNode = lruNodeMap.get(key);
            lruNode.value = value;
            lruNode.ttl = ttl;
            lruNode.timestamp = timestamp;

            deleteLruNode(lruNode);
            insertBeforeHead(lruNode);
            return;
        }


        if (currentCapacity == 0) {
            LruNode<K, V> lruNode = tail.prev;
            K lruKey = lruNode.key;
            lruNodeMap.remove(lruKey);
            deleteLruNode(lruNode);
            currentCapacity++;

        }

        LruNode<K, V> lruNode = new LruNode<>(key, value, ttl, timestamp);
        insertBeforeHead(lruNode);
        lruNodeMap.put(key, lruNode);
        currentCapacity--;

    }


    private void insertBeforeHead(LruNode<K, V> lruNode) {

        LruNode<K, V> afterHead = head.next;
        head.next = lruNode;
        afterHead.prev = lruNode;
        lruNode.prev = head;
        lruNode.next = afterHead;
    }


    private void deleteLruNode(LruNode<K, V> lruNode) {

        LruNode<K, V> before = lruNode.prev;
        LruNode<K, V> after = lruNode.next;
        before.next = after;
        after.prev = before;
        lruNode.prev = null;
        lruNode.next = null;
    }


}
