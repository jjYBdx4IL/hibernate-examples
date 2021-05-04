package com.github.jjYBdx4IL.hibernate.examples;

import com.github.jjYBdx4IL.utils.env.Maven;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Environment;
import org.hibernate.stat.Statistics;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(TestBase.class);
    protected static final String DB_LOCATION = new File(Maven.getMavenTargetDir(), TestBase.class.getSimpleName() + ".db")
            .getAbsolutePath();
    public static final String HIB_JAVAX_CACHE_PROVIDER_KEY = "hibernate.javax.cache.provider";

    protected final Map<String, String> props = new HashMap<>();
    protected EntityManagerFactory emf = null;
    protected EntityManager em = null;
    protected EntityTransaction tx = null;
    protected String puName = "commonPU";

    protected TestBase() {
    	LOG.info("DB_LOCATION: " + DB_LOCATION);
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.show_sql", "true");
        if (props.get("hibernate.cache.use_second_level_cache") == null) {
            props.put("hibernate.cache.use_second_level_cache", "false");
        }
    }

    protected void createEmfEmTx() {
        closeEmfEm();
        LOG.info("creating emf and em for PU " + puName);
        emf = Persistence.createEntityManagerFactory(puName, props);
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    protected void closeEmfEm() {
        if (em != null) {
            LOG.info("closing em");
            em.close();
            em = null;
        }
        if (emf != null) {
            LOG.info("closing emf");
            emf.close();
            emf = null;
        }
    }

    @Before
    public void before() {
        createEmfEmTx();
    }

    @After
    public void after() {
        closeEmfEm();
    }

    @Test
    public void commonTest() throws IOException {
        SomeEntity test = em.find(SomeEntity.class, 1);
        if (test == null) {
            test = new SomeEntity();
            test.id = 1;
            test.data = "a";

            tx.begin();
            em.persist(test);
            tx.commit();
        }
        LOG.info(String.format(Locale.ROOT, "Test{id=%s, data=%s}\n", test.id, test.data));

        SomeEntityWithVersionAndIndex test2 = new SomeEntityWithVersionAndIndex();
        test2.data = "1";
        test2.data2 = "2";
        test2.version = -22;
        test2.id = 0;
        tx.begin();
        em.persist(test2);
        tx.commit();
        LOG.info(test2.toString());
        assertEquals(1, test2.id);
        assertEquals(0, test2.version);

        // object unchanged, version remains the same
        test2.data2 = "2";

        tx.begin();
        em.persist(test2);
        tx.commit();
        LOG.info(test2.toString());
        assertEquals(1, test2.id);
        assertEquals(0, test2.version);

        // object changed, version increases by one
        test2.data2 = "1";

        tx.begin();
        em.persist(test2);
        tx.commit();
        LOG.info(test2.toString());
        assertEquals(1, test2.id);
        assertEquals(1, test2.version);
    }


    protected void setupEhCache() {
        // https://www.boraji.com/hibernate-5-query-cache-entity-cache-and-collection-cache-example
        
        // Enable second level cache (default value is true)
        props.put(Environment.USE_SECOND_LEVEL_CACHE, "true");

        // Enable Query cache
        props.put(Environment.USE_QUERY_CACHE, "true");

        // Specify cache region factory class
        //props.put(Environment.CACHE_REGION_FACTORY, JCacheRegionFactory.class.getName());
        props.put(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.jcache.JCacheRegionFactory");

        // Specify cache provider
        props.put(HIB_JAVAX_CACHE_PROVIDER_KEY, "org.ehcache.jsr107.EhcacheCachingProvider");

        //props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory"); // "jcache"
        props.put("hibernate.javax.cache.missing_cache_strategy",  "create-warn");
        
        // "Only by specifying the second property hibernate.javax.cache.uri will you be able to have a CacheManager per SessionFactory."
        props.put("hibernate.javax.cache.uri", "file:target/classes/ehcache.xml");
        
        props.put("hibernate.generate_statistics", "true");
    }
    
    // called by database-specific test unit child implementations
    @Ignore
    protected void testCachingEnabled() {
        assertCacheStats(0, 0, 0, 0, 0, 0);
        
        LOG.info("1 - persisting entity");
        EntityManager em0 = emf.createEntityManager();
        EntityTransaction tx0 = em0.getTransaction();
        tx0.begin();
        SomeEntityCachable entity0 = new SomeEntityCachable();
        entity0.data = "123";
        em0.persist(entity0);
        tx0.commit();
        em0.close();

        assertCacheStats(1, 0, 0, 0, 0, 0);
        
        LOG.info("2 - retrieving entity");
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

        assertCacheStats(1, 1, 0, 0, 0, 0);
        
        
        LOG.info("3 - retrieving entity via JPA SQL");
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();
        tx2.begin();
        List<SomeEntityCachable> entities2 = em2.createQuery("from SomeEntityCachable", SomeEntityCachable.class)
            .setHint("org.hibernate.cacheable", true)
            .setHint("org.hibernate.cacheMode", "NORMAL")
            .getResultList();
        assertEquals("123", entities2.get(0).data);
        tx2.commit();
        em2.close();
        
        assertCacheStats(1, 1, 0, 1, 0, 1);
        
        LOG.info("4 - retrieving entity via SQL again");
        EntityManager em3 = emf.createEntityManager();
        EntityTransaction tx3 = em3.getTransaction();
        tx3.begin();
        List<SomeEntityCachable> entities3 = em3.createQuery("from SomeEntityCachable", SomeEntityCachable.class)
            .setHint("org.hibernate.cacheable", true)
            .setHint("org.hibernate.cacheMode", "NORMAL")
            .getResultList();
        assertEquals("123", entities3.get(0).data);
        tx3.commit();
        em3.close();
        
        assertCacheStats(1, 2, 0, 1, 1, 1);
        
        
        LOG.info("5 - retrieving entity via Hibernate SQL");
        Session session4 = (Session) em.getDelegate(); // use injected session, don't close it
        Transaction tx4 = session4.beginTransaction();
        List<SomeEntityCachable> entities4 = session4.createQuery("from SomeEntityCachable", SomeEntityCachable.class)
            .setCacheable(true)
            .setCacheMode(CacheMode.NORMAL)
            .list();
        assertEquals("123", entities4.get(0).data);
        tx4.commit();
        // no session close here, this is the managed/injected session
        
        assertCacheStats(1, 3, 0, 1, 2, 1);
        
        LOG.info("6 - retrieving entity via Hibernate SQL again");
        Session session5 = ((Session) em.getDelegate()).getSessionFactory().openSession();
        Transaction tx5 = session5.beginTransaction();
        List<SomeEntityCachable> entities5 = session5.createQuery("from SomeEntityCachable", SomeEntityCachable.class)
            .setCacheable(true)
            .setCacheMode(CacheMode.NORMAL)
            .list();
        assertEquals("123", entities5.get(0).data);
        
        assertCacheStats(1, 4, 0, 1, 3, 1);
        
        List<SomeEntityCachable> entities6 = session5.createQuery("from SomeEntityCachable", SomeEntityCachable.class)
            .setCacheable(true)
            .setCacheMode(CacheMode.NORMAL)
            .list();
        assertEquals("123", entities6.get(0).data);
        tx5.commit();
        session5.close();
        
        // same-session query hit not counted as 2nd level hit
        assertCacheStats(1, 4, 0, 1, 4, 1);
        
        
        
        LOG.info("7 - end; here come the stats:");
        
        Statistics stats = ((Session) em.getDelegate()).getSessionFactory().getStatistics();
        stats.logSummary();

        // these are only the same by accident:
        assertEquals(4, stats.getSecondLevelCacheHitCount());
        assertEquals(4, stats.getQueryCacheHitCount());
        
//        int size = CacheManager.ALL_CACHE_MANAGERS.get(0)
//            .getCache(SomeEntityCachable.class.getName()).getSize();
//        assertThat(size, greaterThan(0));
    }
    
    private void assertCacheStats(int secondLevelPuts, int secondLevelHits, int secondLevelMisses,
        int queryPuts, int queryHits, int queryMisses) {
        Statistics stats = ((Session) em.getDelegate()).getSessionFactory().getStatistics();
        assertEquals(secondLevelPuts, stats.getSecondLevelCachePutCount());
        assertEquals(secondLevelHits, stats.getSecondLevelCacheHitCount());
        assertEquals(secondLevelMisses, stats.getSecondLevelCacheMissCount());
        assertEquals(queryPuts, stats.getQueryCachePutCount());
        assertEquals(queryHits, stats.getQueryCacheHitCount());
        assertEquals(queryMisses, stats.getQueryCacheMissCount());
    }
    
}
