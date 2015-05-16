package com.my.ejb;

/**
 * Created by marcin on 15.05.15.
 */
import com.my.model.Users;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import static junit.framework.Assert.*;

@RunWith(Arquillian.class)
public class DbOperatorTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(DbOperatorTest.class, Users.class)
                //.addPackage(com.my.model.User.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext(unitName="test")
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Test
    public void isEntityManagerNotNull() throws Exception {
        Assert.assertTrue(em != null);
    }

    @Test
    public void isUserTransactionNotNull() throws Exception {
        Assert.assertTrue(utx != null);
    }

    @Test
    public void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        Users u = new Users();
        em.persist(u);
        //em.createQuery("delete from User").executeUpdate();
        utx.commit();
    }


}
