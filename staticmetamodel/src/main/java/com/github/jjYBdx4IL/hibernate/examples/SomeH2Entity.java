package com.github.jjYBdx4IL.hibernate.examples;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
@Entity
public class SomeH2Entity {

    @Id
    @GeneratedValue
    public int id;

    @Basic
    public String data;

    public static TypedQuery<SomeH2Entity> getByDataQuery(EntityManager em, String data) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<SomeH2Entity> criteriaQuery = criteriaBuilder.createQuery(SomeH2Entity.class);
        final Root<SomeH2Entity> userRoot = criteriaQuery.from(SomeH2Entity.class);
        Predicate predicateEmail = criteriaBuilder.equal(
                userRoot.get(SomeH2Entity_.data),
                data);
        criteriaQuery.where(predicateEmail);
        return em.createQuery(criteriaQuery);
    }
}