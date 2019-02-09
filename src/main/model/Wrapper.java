package main.model;

// To be used by the new custom algorithms.
// Extend this class to create your own wrapper class that helps with the removal logic in your algorithm.
public abstract class Wrapper<K,V> {

    private K key;
    private V val;

    protected Wrapper(K key, V val){
        this.key = key;
        this.val = val;
    }

    public K getKey(){
        return key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

}
