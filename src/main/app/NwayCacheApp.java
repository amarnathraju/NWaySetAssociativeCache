package main.app;

import main.model.NCache;
import main.model.NSet;

import java.util.Optional;

public class NwayCacheApp<K,V> {
    private String algoClassPath;

    public String getAlgoClassPath() {
        return algoClassPath;
    }

    private String lruPath = "main.app.LRUSet";
    private String mruPath = "main.app.MRUSet";

    private NCache<K,V> cache;

    // Use the replacementAlgo string to give your own customized Algo.
    // Note that the Class should extend the abstract class 'NSet'
    public NwayCacheApp(int N, int setSize, String replacementAlgo) throws ClassNotFoundException{
        this.cache = new NCache(N, setSize, chooseAlgo(replacementAlgo));
    }

    private Class<NSet<K, V>> chooseAlgo(String replacementAlgo) throws ClassNotFoundException {
        switch (replacementAlgo){
            case "LRU":
                algoClassPath = lruPath;
                break;
            case "MRU":
                algoClassPath = mruPath;
                break;
            default:
                algoClassPath = replacementAlgo;
        }
        return (Class<NSet<K, V>>) Class.forName(algoClassPath);
    }

    public Optional<V> get(K key){
        if(key == null)
            throw new IllegalArgumentException("A key cannot be null");
        return this.cache.get(key);
    }

    public void put(K key,V value){
        if(key == null || value == null)
            throw new IllegalArgumentException("keys and values cannot be null");
        this.cache.put(key,value);
    }

}
