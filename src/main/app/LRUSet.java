package main.app;

import main.model.NSet;
import main.util.Node;
import main.util.DoublyLinkedList;

// Doubly-linkedlist implementation to get/put and remove elements in O(1)
public class LRUSet<K,V> extends NSet<K,V> {
    DoublyLinkedList dll;

    public LRUSet(Integer setSize) {
        super(setSize);
        dll = new DoublyLinkedList();
    }

    @Override
    public Node getWrapper(K key) {
        if(containsKey(key)){
            Node n = (Node) getFromCache(key);
            dll.remove(n);
            dll.setHead(n);
            return n;
        }

        return null;
    }

    @Override
    public void putWrapper(K key, V val) {
        if(containsKey(key)){
            Node old = (Node) getFromCache(key);
            old.setVal(val);
            dll.remove(old);
            dll.setHead(old);
        } else {
            Node created = new Node(key, val);
            dll.setHead(created);
            putInCache(key, created);
        }
    }

    @Override
    public void removeLogic() {
        removeFromCache((K) dll.tail.getKey());
        dll.remove(dll.tail);
    }
}
