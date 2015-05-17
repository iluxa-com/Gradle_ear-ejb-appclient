package com.my.ejb;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.my.remote.CacheBeanRemote;
/**
 * Created by marcin on 02.05.15.
 */
@SuppressWarnings("ALL")
@Startup
@Singleton
public class CacheBean implements CacheBeanRemote{

    // map for cache
    private Map<String, Object> cache = new HashMap<String, Object>();

    // set for calls to registry for timer
    private Set<String> callsSet = new HashSet<>();
    public CacheBean() {};

    @Override
    public void add(String key, Object value) {
        cache.put(key, value);
        callsSet.add(key);
    }

    @Override
    public Object get(String key) {
        if (contains(key)) {
            callsSet.add(key);
            return cache.get(key);
        }
        else return null;
    }

    @Override
    public boolean contains(String key) {
        if (cache.containsKey(key)) {
            callsSet.add(key);
            return true;
        }
        else {
            return false ;
        }
    }

    @Override
    public Object remove(String key) {
        callsSet.remove(key);
        return cache.remove(key);
    }


    @SuppressWarnings("PackageAccessibility")
    @Schedule(dayOfMonth = "*", dayOfWeek =  "*", hour = "*", minute = "0/2")
    void clearCache() {
        // dla kazdego objektu w cache
        for (Map.Entry<String, Object> entry : cache.entrySet() ) {
            // jesli jest w zbiorze odwolan, tylko usun ze zbioru odwolan
            // jesli nie ma usun z cache
            if (callsSet.contains(entry.getKey())) {
                callsSet.remove(entry.getKey());
            }
            else {
                cache.remove(entry.getKey());
                // ew. log
            }
        }
    }

}