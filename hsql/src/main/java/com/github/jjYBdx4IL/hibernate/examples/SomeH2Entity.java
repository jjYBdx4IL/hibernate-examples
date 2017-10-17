package com.github.jjYBdx4IL.hibernate.examples;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
@Entity
public class SomeH2Entity {

    @Id
    @GeneratedValue
    int id;

    @Basic
    String data;
}
