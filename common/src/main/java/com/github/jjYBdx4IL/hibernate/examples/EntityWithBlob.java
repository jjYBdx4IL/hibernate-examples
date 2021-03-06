package com.github.jjYBdx4IL.hibernate.examples;

import java.sql.Blob;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
@Entity
public class EntityWithBlob {

    @Id
    @GeneratedValue
    long id;

    @Basic
    @Column(nullable = false, unique = true, length = 1024)
    String key;

    @Basic
    Blob value;

    @Version
    long version;

}
