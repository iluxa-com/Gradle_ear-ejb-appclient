package com.my.ejb;

import com.my.interceptor.Interceptor;
import com.my.model.Users;
import com.my.remote.UsersCrudRemote;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.*;
import java.util.List;

/**
 * Created by marcin on 16.05.15.
 */

@Stateless
@Interceptors(Interceptor.class)
@TransactionManagement(TransactionManagementType.BEAN)
@Remote(UsersCrudRemote.class)
public class UsersCrudBean implements UsersCrudRemote {

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    @Resource
    SessionContext sc;

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

    @Override
    @Asynchronous
    public List<Users> findByLastName(String lastName) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        utx.begin();
        em.joinTransaction();
        TypedQuery<Users> q = em.createNamedQuery("Users.findByLastName", Users.class);
        q.setParameter("ln", lastName);
        List<Users> result = q.getResultList();
        utx.commit();
        return result;
    }
}
