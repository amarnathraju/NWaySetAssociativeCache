package main.app;

import main.model.NSet;
import main.model.Wrapper;
import main.util.DoublyLinkedList;
import main.util.Node;

// Doubly-linkedlist implementation to get/put and remove elements in O(1)
public class MRUSet<K,V> extends NSet<K ,V> {
    DoublyLinkedList dll;

    public MRUSet(Integer setSize) {
        super(setSize);
        dll = new DoublyLinkedList();
    }

    @Override
    public Wrapper getWrapper(K key) {
        if(containsKey(key)){
            Node n = (Node) getFromCache(key);
            dll.remove(n);
            dll.setTail(n);
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
            dll.setTail(old);
        } else {
            Node created = new Node(key, val);
            dll.setTail(created);
            putInCache(key, created);
        }
    }

    @Override
    public void removeLogic() {
        removeFromCache((K) dll.tail.getKey());
        dll.remove(dll.tail);
    }
}
