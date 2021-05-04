package com.github.jjYBdx4IL.hibernate.examples;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class CachingEnabledTest extends H2TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(CachingEnabledTest.class);
    
    public CachingEnabledTest() {
        super();
        super.setupEhCache();
    }

    @Test
    public void testCachingEnabled() {
        super.testCachingEnabled();
    }

}
