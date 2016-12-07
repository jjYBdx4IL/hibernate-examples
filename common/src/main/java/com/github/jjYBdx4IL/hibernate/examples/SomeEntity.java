package com.github.jjYBdx4IL.hibernate.examples;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
@Entity
@Table(name = "test")
public class SomeEntity {

  @Id
  public int id;

  @Basic
  public String data;
}
