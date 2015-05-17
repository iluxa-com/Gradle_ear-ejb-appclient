package com.my.remote;

/**
 * Created by marcin on 15.05.15.
 */

import com.my.model.Users;

import javax.ejb.Remote;
import javax.transaction.*;
import java.util.List;

@Remote
public interface DbOperatorRemote {
    void addUser(Users user) throws Exception;
    void deleteUser(Users user) throws Exception;
    void updateUser(Users user) throws Exception;
    Users getUserById(int id) throws Exception;
    List<Users> findByLastName(String lastName) throws HeuristicRollbackException, HeuristicMixedException, NotSupportedException, RollbackException, SystemException;
}
