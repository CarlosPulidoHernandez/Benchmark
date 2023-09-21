package com.raffaeleconforti.datastructures.cache.impl;

import com.raffaeleconforti.datastructures.cache.Cache;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Raffaele Conforti (conforti.raffaele@gmail.com) on 2/08/2016.
 */
public class ManualCache<K, V> extends UnifiedMap<K, V> implements Cache<K, V> {

    private Map<K, Long> time = new UnifiedMap<>();

    public ManualCache() {
        super();
    }

    public ManualCache(int initialCapacity) {
        super(initialCapacity);
    }

    public ManualCache(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ManualCache(Map<? extends K, ? extends V> map) {
        super(map);
        Long now = System.currentTimeMillis();
        for(K key : map.keySet()) {
            time.put(key, now);
        }
    }

    public V put(K key, V value) {
        time.put(key, System.currentTimeMillis());
        return super.put(key, value);
    }

    public V get(Object key) {
        if(time.containsKey(key)) {
            time.put((K) key, System.currentTimeMillis());
        }
        return super.get(key);
    }

    public void free() {
        Long now = System.currentTimeMillis();
        Iterator<K> iterator = time.keySet().iterator();
        while(iterator.hasNext()) {
            K key = iterator.next();
            if(now - time.get(key) > 120000) {
                time.remove(key);
                iterator = time.keySet().iterator();
                remove(key);
            }
        }
    }
}
