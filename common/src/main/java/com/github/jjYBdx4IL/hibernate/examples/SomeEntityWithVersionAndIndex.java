package com.github.jjYBdx4IL.hibernate.examples;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
@Entity
@Table(indexes={
    @Index(name="DATA_INDEX", unique=true, columnList="DATA,DATA2")
})
public class SomeEntityWithVersionAndIndex {

    @Id
    @GeneratedValue
    public int id;

    @Basic
    @Column(name="DATA")
    public String data;

    @Basic
    @Column(name="DATA2")
    public String data2;

    @Version
    public long version;

    @Override
    public String toString() {
        return "SomeEntityWithVersionAndIndex{" + "id=" + id + ", data=" + data + ", data2=" + data2 + ", version=" + version + '}';
    }
}
