package com.github.jjYBdx4IL.hibernate.examples;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import net.sf.ehcache.CacheManager;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class CachingEnabledTest extends H2TestBase {

    public CachingEnabledTest() {
        super();
        props.put("hibernate.cache.use_second_level_cache", "true");
        props.put("hibernate.cache.region.factory_class",
            EhCacheRegionFactory.class.getName());
    }

    @Test
    public void testCachingEnabled() {
        EntityManager em0 = emf.createEntityManager();
        EntityTransaction tx0 = em0.getTransaction();
        tx0.begin();
        SomeEntityCachable entity0 = new SomeEntityCachable();
        entity0.data = "123";
        em0.persist(entity0);
        tx0.commit();
        em0.close();

        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();
        SomeEntityCachable entity1 = em1.find(SomeEntityCachable.class, entity0.id);
        tx1.commit();
        em1.close();
        
        assertNotNull(entity0);
        assertNotNull(entity1);
        assertNotEquals(entity0, entity1);
        assertEquals("123", entity1.data);

        int size = CacheManager.ALL_CACHE_MANAGERS.get(0)
            .getCache(SomeEntityCachable.class.getName()).getSize();
        assertThat(size, greaterThan(0));
    }

}
