package com.github.jjYBdx4IL.hibernate.examples;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import static org.junit.Assert.assertArrayEquals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Github jjYBdx4IL Projects
 */
public class BlobStreamTest extends H2TestBase {

    private static final Logger LOG = LoggerFactory.getLogger(BlobStreamTest.class);

    /**
     * as of JPA 2.1, there seems to be no JPA-compliant way of accessing Lobs through streams, so we have to
     * use a provider dependent way to avoid storing large data chunks in memory:
     */
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

}
