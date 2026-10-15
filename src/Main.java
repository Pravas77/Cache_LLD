//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello");

        CacheStrategy<String,Integer> cacheStrategy = new LruCacheStrategy<>(3);
        Cache<String,Integer> cache = new Cache<>(cacheStrategy);


        cache.put("A",1,3600);
        cache.put("B",2,3600);
        cache.put("C",3,2);
        cache.put("D",4,3600);


        if(cache.get("A") != null) System.out.println("A -> " + cache.get("A"));
        else System.out.println("A -> " + "null");

        if(cache.get("B") != null) System.out.println("B -> " + cache.get("B"));
        else System.out.println("B -> " + "null");

        if(cache.get("C") != null) System.out.println("C -> " + cache.get("C"));
        else System.out.println("C -> " + "null");

        if(cache.get("D") != null) System.out.println("D -> " + cache.get("D"));
        else System.out.println("D -> " + "null");


        Thread.sleep(2000);
        System.out.println("waited for 2s");
        if(cache.get("A") != null) System.out.println("A -> " + cache.get("A"));
        else System.out.println("A -> " + "null");

        if(cache.get("B") != null) System.out.println("B -> " + cache.get("B"));
        else System.out.println("B -> " + "null");

        if(cache.get("C") != null) System.out.println("C -> " + cache.get("C"));
        else System.out.println("C -> " + "null");

        if(cache.get("D") != null) System.out.println("D -> " + cache.get("D"));
        else System.out.println("D -> " + "null");



    }
}