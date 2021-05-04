package com.github.jjYBdx4IL.hibernate.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class XmlOnlyPUConfigTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(XmlOnlyPUConfigTest.class);
    
    
    protected EntityManagerFactory emf = null;

    @Before
    public void before() {
        emf = Persistence.createEntityManagerFactory("xmlonlyPU");
    }
    
    @After
    public void after() {
        emf.close();
    }
    
    @Test
    public void test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(new SomeEntity());
        tx.commit();
    }
    
    
}
