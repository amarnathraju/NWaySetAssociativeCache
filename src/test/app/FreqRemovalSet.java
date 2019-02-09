package test.app;

import main.model.NSet;
import main.model.Wrapper;

import java.util.*;
// Testing a new Cache removal strategy.
public class FreqRemovalSet<K,V> extends NSet<K ,V> {

    PriorityQueue<CountWrapper> pQueue;

    public FreqRemovalSet(Integer setSize) {
        super(setSize);
        pQueue = new PriorityQueue<CountWrapper>(setSize, Comparator.comparingInt(CountWrapper::getCount)) ;

    }

    private CountWrapper incCount(CountWrapper n){
        pQueue.remove(n);
        n.updateCount(n.getCount()+1);
        pQueue.add(n);
        return n;
    }

    @Override
    public Wrapper<K, V> getWrapper(K key) {
        if(containsKey(key)){
            CountWrapper n = (CountWrapper) getFromCache(key);
            n = incCount(n);
            return n;
        }
        return null;
    }

    @Override
    public void putWrapper(K key, V val) {
        if(containsKey(key)){
            CountWrapper n = (CountWrapper) getFromCache(key);
            n.setVal(val);
            n = incCount(n);

        } else {
            CountWrapper created = new CountWrapper(key, val);
            putInCache(key, created);
            pQueue.add(created);
        }
    }

    @Override
    public void removeLogic() {
        removeFromCache((K) pQueue.peek().getKey());
        pQueue.poll();
    }
}
