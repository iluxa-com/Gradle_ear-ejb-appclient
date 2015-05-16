package com.my.remote;

import com.my.model.Users;

import javax.ejb.Remote;

/**
 * Created by marcin on 16.05.15.
 */
@Remote
public interface UsersCrudRemote extends GenericCrudRemote<Users,Integer>{

}
