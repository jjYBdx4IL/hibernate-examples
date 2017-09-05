package com.github.jjYBdx4IL.hibernate.examples;

import static org.junit.Assert.assertEquals;

import com.github.jjYBdx4IL.utils.jdbc.ResultSetUtils;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.persistence.TypedQuery;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class ProgrammaticH2ExampleTest extends H2TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticH2ExampleTest.class);

    public ProgrammaticH2ExampleTest() {
        puName = "h2PU2";
    }

    @Test
    public void testStaticMetaModel() {
        SomeH2EntityB a = new SomeH2EntityB();
        a.data = "test";

        tx.begin();
        em.persist(a);
        tx.commit();

        TypedQuery<SomeH2EntityB> query = QueryFactory.getByDataQuery(em, "test");
        List<SomeH2EntityB> result = query.getResultList();

        assertEquals(1, result.size());
    }

    @Test
    public void testLike() {
        tx.begin();
        SomeH2EntityB e1 = new SomeH2EntityB();
        e1.data = "abc";
        em.persist(e1);

        SomeH2EntityB e2 = new SomeH2EntityB();
        e2.data = "DEF";
        em.persist(e2);

        tx.commit();

        assertEquals(1, QueryFactory.prefixMatch(em, "a").getResultList().size());
        assertEquals(1, QueryFactory.prefixMatch(em, "A").getResultList().size());
        LOG.info(
            StringUtils.join(QueryFactory.prefixMatch(em, "j3h45jh3j45h").getResultList(), System.lineSeparator()));
        assertEquals(0, QueryFactory.prefixMatch(em, "j3h45jh3j45h").getResultList().size());
    }
}
