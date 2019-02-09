package main.util;


import main.model.Wrapper;

public class Node<K,V> extends Wrapper<K,V> {

    public Node pre;
    public Node next;

    public Node(K key, V val){
        super(key,val);
    }

}