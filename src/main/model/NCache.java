package main.model;


import main.app.LRUSet;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;


public class NCache<K,V> {

    private int N, setSize;
    private NSet<K,V>[] sets;

    public NCache(int N, int setSize, Class<NSet<K, V>> setClass) {
        this.N = N;
        this.setSize = setSize;

        try {
            sets = (NSet<K, V>[]) Array.newInstance(setClass, N);
            Class[] cArg = new Class[1];
            cArg[0] = Integer.class;
            for (int i = 0; i < N; i++) {
                sets[i] = setClass.getDeclaredConstructor(cArg).newInstance(this.setSize);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
            e.printStackTrace();
            System.out.println("Defaulting to use LRU logic because the given Algo/class pair were not found");
            sets = new LRUSet[N];
            for(int i = 0 ; i < N ; i++) {
                sets[i] = new LRUSet<>(this.setSize);
            }
        }
    }

    public Optional<V> get(K key){
        return sets[Math.abs(key.hashCode()) % N].get(key);
    }

    public void put(K key,V value){
        sets[Math.abs(key.hashCode()) % N].put(key,value);
    }
}
