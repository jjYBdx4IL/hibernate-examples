package com.github.jjYBdx4IL.hibernate.examples;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    private static final String DERBY_LOG_PROPNAME = "derby.stream.error.file";

    public static void setDerbyLogFile(File logFile) {
        final String derbyLog = logFile.getAbsolutePath();
        if (!System.getProperty(DERBY_LOG_PROPNAME, "").equals(derbyLog)) {
            LOG.info("setting derby log file to " + derbyLog);
            System.setProperty("derby.stream.error.file", derbyLog);
        }
    }

}
