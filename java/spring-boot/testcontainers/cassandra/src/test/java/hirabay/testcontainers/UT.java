package hirabay.testcontainers;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import hirabay.testcontainers.domain.Sample;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.InetSocketAddress;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Testcontainers
public class UT {
    @Container
    public static CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2")
            .withInitScript("initial.cql")
            .withExposedPorts(9042);

    @Test
    void test() {
        assertThat(cassandra.isRunning()).isTrue();

        CqlSession cqlSession = CqlSession.builder()
                .addContactPoint(cassandra.getContactPoint())
                .withLocalDatacenter(cassandra.getLocalDatacenter())
                .build();

        var template = new CassandraTemplate(cqlSession);
        var selected = template.select("SELECT * from sample.t_sample", Sample.class);
        log.info("selected: {}", selected);
    }
}
