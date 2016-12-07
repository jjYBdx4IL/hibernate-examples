package com.github.jjYBdx4IL.hibernate.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class ProgrammaticH2ExampleTest extends TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticH2ExampleTest.class);

    public ProgrammaticH2ExampleTest() {
        super();
        // http://www.h2database.com/html/cheatSheet.html
        props.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        props.put("javax.persistence.jdbc.url", "jdbc:h2:" + DB_LOCATION);
//        props.put("javax.persistence.jdbc.user", "sa");
//        props.put("javax.persistence.jdbc.password", "");
    }

}
