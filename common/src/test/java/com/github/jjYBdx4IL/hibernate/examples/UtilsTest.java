package com.github.jjYBdx4IL.hibernate.examples;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class UtilsTest {

    @Test
    public void testQuoteDbName() {
        assertEquals("\\\\", Utils.quoteDbName("\\"));
        assertEquals("\\:", Utils.quoteDbName(":"));
        assertEquals("\\;", Utils.quoteDbName(";"));
    }

}
