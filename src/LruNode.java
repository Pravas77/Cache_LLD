public class LruNode<K,V> {

    K key;
    V value;
    LruNode<K,V> prev;
    LruNode<K,V> next;
    long ttl;
    long timestamp;

    public LruNode(K key, V value, long ttl, long timestamp) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
        this.ttl = ttl;
        this.timestamp = timestamp;
    }
}
