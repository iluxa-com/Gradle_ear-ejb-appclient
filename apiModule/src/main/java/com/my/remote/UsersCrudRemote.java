package com.my.remote;

import com.my.model.Users;

import javax.ejb.Remote;
import javax.mail.MessagingException;
import javax.transaction.*;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by marcin on 16.05.15.
 */
@Remote
public interface UsersCrudRemote extends GenericCrudRemote<Users,Integer>{

    List<Users> findByLastName(String lastName) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    Future<Users> findByLastNameAsynchronous(String lastName) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException;
    void sendMail(String to,String subject, String body) throws MessagingException;
}
