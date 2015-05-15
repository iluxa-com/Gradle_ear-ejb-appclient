package com.my.ejb;

import com.my.remote.CacheBeanRemote;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * Created by marcin on 12.05.15.
 */
@RunWith(Arquillian.class)
public class CacheBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                //.addPackages(true, new ExcludeRegExpPaths(".*Test.class$"), PROJECT_ROOT_PACKAGE)
                .addClasses(CacheBeanTest.class, CacheBean.class, CacheBeanRemote.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @EJB
    private static CacheBeanRemote cache;

    @Test
    public void should_create_greeting() {
        //Assert.fail("Not yet implemented");
        Assert.assertTrue("1 equal 1 - fail",1==1 );
    }

    @Test
    public void cacheIsNotNull() {
        Assert.assertTrue(cache != null);
    }

    @Test
    public void addToCache() {
        cache.add("1",1);
        Assert.assertTrue("Powinien zawierac 1",  cache.contains("1"));
        cache.remove("1");
    }


}