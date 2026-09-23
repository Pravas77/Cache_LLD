public class Cache<K,V> {

    private  CacheStrategy<K,V> cacheStrategy;

    public Cache(CacheStrategy<K, V> cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    public V get(K key) {
        return cacheStrategy.get(key);
    }

    public void put(K key, V value, long ttl){
        cacheStrategy.put(key, value, ttl);
    }
}
