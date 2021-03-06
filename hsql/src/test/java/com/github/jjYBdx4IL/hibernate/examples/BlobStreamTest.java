package com.github.jjYBdx4IL.hibernate.examples;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import com.github.jjYBdx4IL.utils.env.Surefire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class BlobStreamTest extends HSQLTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(BlobStreamTest.class);

    /**
     * as of JPA 2.1, there seems to be no JPA-compliant way of accessing Lobs
     * through streams, so we have to use a provider dependent way to avoid
     * storing large data chunks in memory:
     */
    @Test
    public void testBlobStream() throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("7878");
        byte[] data = sb.toString().getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        Session session = (Session) em.getDelegate();
        Blob blob = Hibernate.getLobCreator(session).createBlob(bais, data.length);

        EntityWithBlob ewb = new EntityWithBlob();
        ewb.key = "123";
        ewb.value = blob;
        tx.begin();
        em.persist(ewb);
        tx.commit();

        EntityWithBlob ewb2 = em.find(EntityWithBlob.class, ewb.id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InputStream is = ewb2.value.getBinaryStream()) {
            IOUtils.copy(is, baos);
        }

        assertArrayEquals(data, baos.toByteArray());
    }

    @Test
    public void testBlobStreamCompact() throws SQLException, IOException {
        assumeTrue(Surefire.isSingleTestExecution());

        long dbSizeAtStart = FileUtils.sizeOfDirectory(new File(DB_LOCATION));
        
        byte[] data = new byte[1048576];

        for (int i = 0; i < 16; i++) {
            tx.begin();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);

            Session session = (Session) em.getDelegate();
            Blob blob = Hibernate.getLobCreator(session).createBlob(bais, data.length);

            EntityWithBlob ewb = new EntityWithBlob();
            ewb.key = "123" + i;
            ewb.value = blob;
            em.persist(ewb);
            tx.commit();
        }
        tx.begin();
        em.createNativeQuery("delete from EntityWithBlob").executeUpdate();
        tx.commit();
        tx.begin();
        em.createNativeQuery("CHECKPOINT").executeUpdate();
        em.createNativeQuery("SHUTDOWN COMPACT").executeUpdate();
        tx.commit();
        
        long dbSizeIncreased = FileUtils.sizeOfDirectory(new File(DB_LOCATION)) - dbSizeAtStart;
        assertTrue(dbSizeIncreased < data.length);
    }
}
