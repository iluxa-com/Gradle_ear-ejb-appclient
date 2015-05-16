package com.my.remote;

import javax.ejb.Remote;

/**
 * Created by marcin on 16.05.15.
 */
@Remote
public interface GenericCrudRemote<T,K>  {

    void save(T t) throws Exception;
    void delete (T t) throws Exception;
    void update (T t) throws Exception;
    T findById(K id) throws Exception;
}