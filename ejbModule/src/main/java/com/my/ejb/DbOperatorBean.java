package com.my.ejb;

import com.my.model.Users;
import com.my.remote.DbOperatorRemote;
import com.my.remote.UsersCrudRemote;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.transaction.*;
import java.util.List;

/**
 * Created by marcin on 15.05.15.
 */
@Startup
@Singleton
public class DbOperatorBean implements DbOperatorRemote {

    @EJB
    private UsersCrudRemote usersCrudRemote;

    @Override
    public void addUser(Users user) throws Exception {
    usersCrudRemote.save(user);
    }

    @Override
    public void deleteUser(Users user) throws Exception {
    usersCrudRemote.delete(user);
    }

    @Override
    public void updateUser(Users user) throws Exception {
    usersCrudRemote.update(user);
    }

    @Override
    public Users getUserById(int id) throws Exception {
        return usersCrudRemote.findById(id);
    }

    @Override
    public List<Users> findByLastName(String lastName) throws HeuristicRollbackException, HeuristicMixedException, NotSupportedException, RollbackException, SystemException {
        return usersCrudRemote.findByLastName(lastName);
    }
}
