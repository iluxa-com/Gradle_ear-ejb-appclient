package com.my.remote;

import com.my.model.Users;

import javax.ejb.Remote;
import javax.transaction.*;
import java.util.List;

/**
 * Created by marcin on 16.05.15.
 */
@Remote
public interface UsersCrudRemote extends GenericCrudRemote<Users,Integer>{

    List<Users> findByLastName(String lastName) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
}
