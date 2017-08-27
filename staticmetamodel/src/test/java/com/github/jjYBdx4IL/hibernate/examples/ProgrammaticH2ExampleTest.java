package com.github.jjYBdx4IL.hibernate.examples;

import java.util.List;

import javax.persistence.TypedQuery;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class ProgrammaticH2ExampleTest extends H2TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticH2ExampleTest.class);

    @Test
    public void testStaticMetaModel() {
        createEmfEmTx("h2PU");

        SomeH2Entity a = new SomeH2Entity();
        a.data = "test";
        
        tx.begin();
        em.persist(a);
        tx.commit();

        TypedQuery<SomeH2Entity> query = QueryFactory.getByDataQuery(em, "test");
        List<SomeH2Entity> result = query.getResultList();

        assertEquals(1, result.size());
    }

}
