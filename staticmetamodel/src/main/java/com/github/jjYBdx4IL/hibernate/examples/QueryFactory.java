package com.github.jjYBdx4IL.hibernate.examples;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class QueryFactory {

    public static TypedQuery<SomeH2EntityB> getByDataQuery(EntityManager em, String data) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<SomeH2EntityB> cq = cb.createQuery(SomeH2EntityB.class);
        final Root<SomeH2EntityB> userRoot = cq.from(SomeH2EntityB.class);
        cq.where(cb.equal(userRoot.get(SomeH2EntityB_.data), data));
        return em.createQuery(cq);
    }

    public static TypedQuery<SomeH2EntityB> prefixMatch(EntityManager em, String prefix) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<SomeH2EntityB> cq = cb.createQuery(SomeH2EntityB.class);
        final Root<SomeH2EntityB> root = cq.from(SomeH2EntityB.class);
        cq.where(cb.like(cb.lower(root.get(SomeH2EntityB_.data)), prefix.toLowerCase() + "%"));
        return em.createQuery(cq);
    }
    
}
