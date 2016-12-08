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
public class ProgrammaticH2ExampleTest extends H2TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticH2ExampleTest.class);

    // each persistence unit is only using entities from its own module
    // (that is why we need at least one persistence.xml specifying the PU's name in each package that
    // is holding persistence entitiee)
    @Test
    public void testMultiplePersistenceXMLsInDifferentPackages() {
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
