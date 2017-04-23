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
    int id;

    @Basic
    @Column(name="DATA")
    String data;

    @Basic
    @Column(name="DATA2")
    String data2;

    @Version
    long version;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SomeEntityWithVersionAndIndex [id=");
		builder.append(id);
		builder.append(", data=");
		builder.append(data);
		builder.append(", data2=");
		builder.append(data2);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
