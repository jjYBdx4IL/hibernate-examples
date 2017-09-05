package com.github.jjYBdx4IL.hibernate.examples;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class H2TestBase extends TestBase {

    public H2TestBase() {
        super();
        // http://www.h2database.com/html/cheatSheet.html
        props.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        props.put("javax.persistence.jdbc.url", "jdbc:h2:" + DB_LOCATION);
//        props.put("javax.persistence.jdbc.user", "sa");
//        props.put("javax.persistence.jdbc.password", "");
    }


}
