public interface CacheStrategy<K, V> {

    public V get(K key);

    public void put(K key, V value, long ttl);
}
