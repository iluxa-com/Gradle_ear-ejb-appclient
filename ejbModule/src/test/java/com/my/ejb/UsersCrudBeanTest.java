package com.my.ejb;

import com.my.model.Users;
import com.my.remote.GenericCrudRemote;
import com.my.remote.UsersCrudRemote;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

/**
 * Created by marcin on 16.05.15.
 */
@RunWith(Arquillian.class)
public class UsersCrudBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(UsersCrudBean.class, Users.class, UsersCrudRemote.class, GenericCrudRemote.class)
                        //.addPackage(com.my.model.User.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private static  UsersCrudRemote usersCrudBean;


    @Test
    public void testSave() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");

        usersCrudBean.save(user);


    }

    @Test
    public void testDelete() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");

        usersCrudBean.save(user);
    }

    @Test
    public void testUpdate() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");

        usersCrudBean.save(user);
    }

    @Test
    public void testFindById() throws Exception {
        Users user = new Users();
        user.setFirstName("a");
        user.setLastName("b");

        usersCrudBean.save(user);
    }
}