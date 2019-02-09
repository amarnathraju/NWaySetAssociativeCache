package main.util;

import main.model.Wrapper;

// This class is to be used by the application user if need.
public class SimpleWrapper<K ,V> extends Wrapper<K,V> {

    protected SimpleWrapper(K key, V val) {
        super(key, val);
    }

}
