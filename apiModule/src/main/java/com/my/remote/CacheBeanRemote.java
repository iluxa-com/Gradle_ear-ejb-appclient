package com.my.remote;
import javax.ejb.Remote;

/**
 * Created by marcin on 02.05.15.
 */

@Remote
public interface CacheBeanRemote {
    void add(String key,Object value);
    Object get(String key);
    boolean contains(String key);
    Object remove(String key);

}
