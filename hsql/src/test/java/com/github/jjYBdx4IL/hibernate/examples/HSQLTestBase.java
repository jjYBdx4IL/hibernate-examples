package com.github.jjYBdx4IL.hibernate.examples;

import java.io.File;

/**
 * 
 * @author Github jjYBdx4IL Projects
 */
public class HSQLTestBase extends TestBase {

    public HSQLTestBase() {
        super();
        // http://www.java2s.com/Tutorial/Java/0350__Hibernate/HibernateandHSQL.htm
        props.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
        props.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:" + DB_LOCATION + File.separatorChar +
            "db;hsqldb.tx=mvcc;hsqldb.tx_level=SERIALIZABLE");
        // props.put("javax.persistence.jdbc.user", "sa");
        // props.put("javax.persistence.jdbc.password", "");
    }

}
