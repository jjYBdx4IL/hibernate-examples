package com.github.jjYBdx4IL.hibernate.examples;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Environment;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class H2TestBase extends TestBase {

    public H2TestBase() {
        super();
        props.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
        props.put(Environment.DRIVER, "org.h2.Driver");
        props.put(Environment.URL, "jdbc:h2:" + DB_LOCATION);
        //props.put(Environment.URL, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"); // keep the mem db for the duration of the JVM
    }


}
