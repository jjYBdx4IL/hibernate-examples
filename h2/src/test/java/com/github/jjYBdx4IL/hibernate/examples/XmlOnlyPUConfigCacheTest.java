package com.github.jjYBdx4IL.hibernate.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class XmlOnlyPUConfigCacheTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(XmlOnlyPUConfigCacheTest.class);
    
    protected EntityManagerFactory emf = null;

    @Before
    public void before() {
        emf = Persistence.createEntityManagerFactory("xmlonlyCachePU");
    }
    
    @After
    public void after() {
        emf.close();
    }
    
    @Test
    public void test() {
        LOG.info("1 - persist POJO");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(new SomeEntityCachable());
        tx.commit();
        em.close();
        
        LOG.info("2 - retrieve POJO");
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();
        tx2.begin();
        assertNotNull(em2.find(SomeEntityCachable.class, 0));
        tx2.commit();
        em2.close();
        
        LOG.info("3 - retrieving entity via SQL (JPA)");
        EntityManager em3 = emf.createEntityManager();
        EntityTransaction tx3 = em3.getTransaction();
        tx3.begin();
        List<SomeEntityCachable> entities3 = em3.createQuery("from SomeEntityCachable", SomeEntityCachable.class)
            .setHint("org.hibernate.cacheable", true)
            .setHint("org.hibernate.cacheMode", "NORMAL")
            .getResultList();
        assertEquals(1, entities3.size());
        tx3.commit();
        em3.close();
        
        LOG.info("4 - retrieving entity via SQL (Hibernate-native)");
        Session session4 = (Session) emf.createEntityManager().getDelegate(); // use injected session, don't close it
        Transaction tx4 = session4.beginTransaction();
        List<SomeEntityCachable> entities4 = session4.createQuery("from SomeEntityCachable", SomeEntityCachable.class)
            .setCacheable(true)
            .setCacheMode(CacheMode.NORMAL)
            .list();
        assertEquals(1, entities4.size());
        tx4.commit();
        session4.close();
        
        LOG.info("5 - verifying cache ops via stats");
        EntityManager em5 = emf.createEntityManager();
        Statistics stats = ((Session)em5.getDelegate()).getSessionFactory().getStatistics();
        // 1x POJO + 1x QUERY:
        assertEquals(2, stats.getSecondLevelCacheHitCount());
        stats.logSummary();
        em5.close();
    }
}
