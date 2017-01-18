package com.lewis.firstPhase.multiThreadUp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/18.
 */
public class MegerHashMap<K,V> extends HashMap<K,V> {

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        super.putAll(m);
    }
}
