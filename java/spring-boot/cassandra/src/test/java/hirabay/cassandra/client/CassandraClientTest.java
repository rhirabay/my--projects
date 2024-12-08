package hirabay.cassandra.client;

import com.datastax.oss.driver.api.core.CqlSession;
import hirabay.cassandra.domain.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class CassandraClientTest {
    @Container
    public static CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2")
            .withInitScript("initial.cql")
            .withExposedPorts(9042);

    @Test
    void test() throws Exception {
        CqlSession cqlSession = CqlSession.builder()
                .addContactPoint(cassandra.getContactPoint())
                .withLocalDatacenter(cassandra.getLocalDatacenter())
                .build();
        var template = new CassandraTemplate(cqlSession);
        var actual = template.select("SELECT * FROM sample.t_sample", Sample.class);

        System.out.println(actual);
    }
}