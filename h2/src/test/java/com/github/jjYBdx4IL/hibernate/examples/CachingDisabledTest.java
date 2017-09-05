package com.github.jjYBdx4IL.hibernate.examples;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class CachingDisabledTest extends H2TestBase {

    @Test
    public void testCachingDisabled() {
        EntityManager em0 = emf.createEntityManager();
        EntityTransaction tx0 = em.getTransaction();
        SomeEntity entity0 = new SomeEntity();
        tx0.begin();
        em0.persist(entity0);
        tx0.commit();
        em0.close();
        
        EntityManager em1 = emf.createEntityManager();
        SomeEntity entity1 = em1.find(SomeEntity.class, entity0.id);
        assertNotEquals(entity0, entity1);
    }

}
