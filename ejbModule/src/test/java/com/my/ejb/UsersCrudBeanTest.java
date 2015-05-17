package com.my.ejb;

import com.my.interceptor.Interceptor;
import com.my.model.Users;
import com.my.remote.GenericCrudRemote;
import com.my.remote.UsersCrudRemote;
import org.fest.assertions.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by marcin on 16.05.15.
 */
@RunWith(Arquillian.class)
public class UsersCrudBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(UsersCrudBean.class, Users.class, UsersCrudRemote.class, GenericCrudRemote.class,
                        Interceptor.class)
                        //.addPackage(com.my.model.User.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("log4j.properties", "META-INF/log4j.properties")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private static  UsersCrudRemote usersCrudBean;

    @PersistenceContext(unitName="test")
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Before
    public void clearTable() throws Exception{
        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from Users").executeUpdate();
        utx.commit();
    }

    @Test
    public void testSave() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");

        usersCrudBean.save(user);
        List<Users> result = usersCrudBean.findByLastName("b");
        assertTrue("result = 1",result.size() == 1 );
        for (Users u : result) {
            assertTrue("last name", u.getLastName().equals("b"));
        }
    }

    @Test
    public void testDelete() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");

        usersCrudBean.save(user);
        List<Users> result = usersCrudBean.findByLastName("b");
        assertTrue("result = 1", result.size() == 1);
        for (Users u : result) {
            assertTrue("last name", u.getLastName().equals("b"));
            usersCrudBean.delete(u);
        }
        result = usersCrudBean.findByLastName("b");
        assertTrue("result = 0", result.size() == 0);

    }

    @Test
    public void testUpdate() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");

        usersCrudBean.save(user);
        List<Users> result = usersCrudBean.findByLastName("b");
        assertTrue("result = 1", result.size() == 1);
        for (Users u : result) {
            assertTrue("last name", u.getLastName().equals("b"));
            u.setLastName("c");
            usersCrudBean.update(u);
        }

        result = usersCrudBean.findByLastName("c");
        for (Users u : result) {
            assertTrue("last name", u.getLastName().equals("c"));
        }
    }

    @Test
    public void testFindById() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");
        usersCrudBean.save(user);

        List<Users> result = usersCrudBean.findByLastName("b");
        assertTrue("result = 1", result.size() == 1);
        for (Users u : result) {
            Users result2 = usersCrudBean.findById(u.getId());
            assertTrue("id > 0 ", result2.getId() > 0);
        }
    }
}