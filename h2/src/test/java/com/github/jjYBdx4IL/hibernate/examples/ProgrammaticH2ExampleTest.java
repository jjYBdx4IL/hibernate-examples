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

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class ProgrammaticH2ExampleTest {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticH2ExampleTest.class);
    private static final String DB_LOCATION = Utils.quoteDbName(new File(Maven.getMavenTargetDir(), ProgrammaticH2ExampleTest.class.getSimpleName() + ".h2")
            .getAbsolutePath());

    @BeforeClass
    public static void beforeClass() {
        Utils.setDerbyLogFile(new File(Maven.getMavenTargetDir(), "derby.log"));
    }

    @Test
    public void test() throws IOException {
        // http://www.h2database.com/html/cheatSheet.html
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        props.put("javax.persistence.jdbc.url", "jdbc:h2:" + DB_LOCATION);
//        props.put("javax.persistence.jdbc.user", "sa");
//        props.put("javax.persistence.jdbc.password", "");
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.show_sql", "true");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test", props);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

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

        em.close();
        emf.close();
    }

}
