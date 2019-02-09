package main.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class NSet<K,V> {

    private final Integer setSize; // Final variable, can't be changed once set
    private final Map<K,Wrapper<K,V>> cache;

    // Abstract methods that need to be implemented by the sub-classes;
    public abstract Wrapper<K,V> getWrapper(K key);

    public abstract void putWrapper(K key, V val);

    public abstract void removeLogic();


    // Constructor the created the actual cache Map
    protected NSet(Integer setSize){
        this.setSize = setSize;
        this.cache = new HashMap<>(this.setSize);
    }

    // Methods to access the cache map. We don't want to make the cache map accessible to the sub-classes for security reasons.
    protected boolean containsKey(K key){
        return cache.containsKey(key);
    }

    protected Wrapper<K,V> getFromCache(K key){
        return cache.get(key);
    }

    protected void putInCache(K key, Wrapper<K,V> wrapperObj){
        cache.put(key, wrapperObj);
    }

    protected void removeFromCache(K key){
        cache.remove(key);
    }


    // The public APIs used by the classes that use the Cache.
    public Optional<V> get(K key){
        Wrapper<K,V> w = getWrapper(key);
        return (w!=null) ? Optional.ofNullable(w.getVal()) : Optional.empty();
    }

    public void put(K key, V val){

        if(!this.cache.containsKey(key)) {
            // Ensures that the Set has at most setSize-1 elements before we add a new element;
            while (this.cache.size() >= setSize) {
                removeLogic();
            }
        }

        putWrapper(key, val);

    }

}
