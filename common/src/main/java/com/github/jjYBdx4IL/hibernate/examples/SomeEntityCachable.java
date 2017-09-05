package com.github.jjYBdx4IL.hibernate.examples;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
@Entity
@Table(name = "testCachable")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SomeEntityCachable {

  @Id
  int id;

  @Basic
  String data;
}
