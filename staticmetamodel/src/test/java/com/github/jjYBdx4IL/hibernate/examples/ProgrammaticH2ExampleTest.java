package com.github.jjYBdx4IL.hibernate.examples;

import static org.junit.Assert.*;

import com.github.jjYBdx4IL.utils.jdbc.ResultSetUtils;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

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
    
    @Test
    public void testCollection() {
        tx.begin();
        Article article = new Article();
        article.setContent("content");
        article.setTitle("title");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        tags.add(new Tag("tag3"));
        article.setTags(tags);
        em.persist(article);
        tx.commit();
        
        Article a1 = em.find(Article.class, article.getId());
        assertNotNull(a1);
        assertEquals(3, a1.getTags().size());
    }
    
    @Test
    public void testFilterByJoinedTable() {
        tx.begin();
        Article article = new Article();
        article.setContent("content1");
        article.setTitle("title1");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag4"));
        tags.add(new Tag("tag5"));
        tags.add(new Tag("tag6"));
        article.setTags(tags);
        em.persist(article);
        
        article = new Article();
        article.setContent("content2");
        article.setTitle("title2");
        tags = new HashSet<>();
        tags.add(new Tag("tag7"));
        tags.add(new Tag("tag8"));
        tags.add(new Tag("tag9"));
        article.setTags(tags);
        em.persist(article);
        tx.commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> q = cb.createQuery(Article.class);
        Root<Article> c = q.from(Article.class);
        Join<Article, Tag> p = c.join(Article_.tags, JoinType.INNER);
        q.where(cb.equal(p.get(Tag_.name), "tag5"));
        TypedQuery<Article> query = em.createQuery(q);
        List<Article> result = query.getResultList();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("content1", result.get(0).getContent());
        assertEquals(3, result.get(0).getTags().size());
    }
}
