package com.github.jjYBdx4IL.hibernate.examples;

import com.github.jjYBdx4IL.utils.env.Maven;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.io.FileUtils;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://stackoverflow.com/questions/8459284/using-hibernate-with-embedded-derby
 * http://stackoverflow.com/questions/6074678/setting-properties-programmatically-in-hibernate
 *
 * @author Github jjYBdx4IL Projects
 */
public class ProgrammaticDerbyExampleTest {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticDerbyExampleTest.class);
    private static final String DB_LOCATION = Utils.quoteDbName(new File(Maven.getMavenTargetDir(), ProgrammaticDerbyExampleTest.class.getSimpleName() + ".derby")
            .getAbsolutePath());

    @BeforeClass
    public static void beforeClass() {
        Utils.setDerbyLogFile(new File(Maven.getMavenTargetDir(), "derby.log"));
    }

    @Test
    public void test() throws IOException {
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        props.put("javax.persistence.jdbc.url", "jdbc:derby:" + DB_LOCATION + ";create=true");
        props.put("javax.persistence.jdbc.user", "root");
        props.put("javax.persistence.jdbc.password", "root");
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

        try {
            DriverManager.getConnection("jdbc:derby:" + DB_LOCATION + ";shutdown=true");
            fail();
        } catch (SQLException ex) {
            FileUtils.deleteDirectory(new File(DB_LOCATION));
        }
    }

}
