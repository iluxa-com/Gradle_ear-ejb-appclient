package com.my.remote;

/**
 * Created by marcin on 15.05.15.
 */

import com.my.model.User;

import javax.ejb.Remote;

@Remote
public interface DbOperatorRemote {
    void addUser(User user);
}
