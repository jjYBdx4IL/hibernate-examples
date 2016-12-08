package com.github.jjYBdx4IL.hibernate.examples;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class ProgrammaticH2ExampleTest extends TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticH2ExampleTest.class);

    public ProgrammaticH2ExampleTest() {
        super();
        // http://www.h2database.com/html/cheatSheet.html
        props.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        props.put("javax.persistence.jdbc.url", "jdbc:h2:" + DB_LOCATION);
//        props.put("javax.persistence.jdbc.user", "sa");
//        props.put("javax.persistence.jdbc.password", "");
    }

    // each persistence unit is only using entities from its own module
    @Test
    public void testMultiplePersistenceXMLsInDIfferentPackages() {
        createEmfEmTx("h2PU");

        try {
            SomeEntity test = em.find(SomeEntity.class, 3);
            fail();
        } catch (IllegalArgumentException ex) {
        }

        SomeH2Entity test = em.find(SomeH2Entity.class, 1);
        assertNull(test);
    }

}
