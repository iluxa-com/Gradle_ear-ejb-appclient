package com.my.ejb;

import com.my.model.Users;
import com.my.remote.DbOperatorRemote;

import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by marcin on 15.05.15.
 */
@Startup
@Singleton
public class DbOperatorBean implements DbOperatorRemote {


    @Override
    public void addUser(Users users) {
    // tu wstrzyknac stateless bean
    }
}
