package test.app;

import main.model.Wrapper;

public class CountWrapper<K,V> extends Wrapper<K,V> {

    private Integer count;

    public CountWrapper(K key, V val){
        super(key, val);
        this.count = 1;
    }

    public Integer getCount(){
        return this.count;
    }


    public void updateCount(int count){
        this.count = count;
    }

}
