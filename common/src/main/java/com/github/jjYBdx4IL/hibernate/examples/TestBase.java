package com.github.jjYBdx4IL.hibernate.examples;

import com.github.jjYBdx4IL.utils.env.Maven;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
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

    protected final Map<String, String> props = new HashMap<>();
    protected EntityManagerFactory emf = null;
    protected EntityManager em = null;
    protected EntityTransaction tx = null;

    protected TestBase() {
    	LOG.info("DB_LOCATION: " + DB_LOCATION);
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.show_sql", "true");
    }

    protected void createEmfEmTx(String puName) {
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
        createEmfEmTx("commonPU");
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

}
