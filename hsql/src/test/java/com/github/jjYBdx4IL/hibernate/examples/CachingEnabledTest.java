package com.github.jjYBdx4IL.hibernate.examples;

import org.junit.Test;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class CachingEnabledTest extends HSQLTestBase {

    public CachingEnabledTest() {
        super();
        super.setupEhCache();
    }

    @Test
    public void testCachingEnabled() {
        super.testCachingEnabled();
    }

}
