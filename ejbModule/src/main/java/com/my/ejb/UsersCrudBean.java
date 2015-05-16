package com.my.ejb;

import com.my.model.Users;
import com.my.remote.UsersCrudRemote;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 * Created by marcin on 16.05.15.
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Remote(UsersCrudRemote.class)
public class UsersCrudBean implements UsersCrudRemote {

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    @Override
    public void save(Users user) throws Exception{
        utx.begin();
        em.joinTransaction();
        em.persist(user);
        utx.commit();
    }

    @Override
    public void delete(Users user) throws Exception {
        utx.begin();
        em.joinTransaction();
        user = em.merge(user);
        em.remove(user);
        utx.commit();
    }

    @Override
    public void update(Users user) throws Exception{
        utx.begin();
        em.joinTransaction();
        em.merge(user);
        utx.commit();
    }

    @Override
    public Users findById(Integer id)throws Exception {
        utx.begin();
        em.joinTransaction();
        Users dto = em.find(Users.class, id);
        utx.commit();
        return dto;
    }
}
