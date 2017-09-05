package com.github.jjYBdx4IL.hibernate.examples;

import com.github.jjYBdx4IL.utils.env.Maven;

import java.io.File;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class ProgrammaticDerbyExampleTest extends TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticDerbyExampleTest.class);

    @BeforeClass
    public static void beforeClass() {
        Utils.setDerbyLogFile(new File(Maven.getMavenTargetDir(), "derby.log"));
    }

    public ProgrammaticDerbyExampleTest() {
        super();
        props.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        props.put("javax.persistence.jdbc.url", "jdbc:derby:" + DB_LOCATION + ";create=true");
//        props.put("javax.persistence.jdbc.user", "root");
//        props.put("javax.persistence.jdbc.password", "root");
    }
    
    
}
