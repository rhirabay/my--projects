package hirabay.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import com.datastax.oss.driver.api.core.CqlSession;
import hirabay.testcontainers.domain.Sample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers
public class UT {
    @Container
    public static CassandraContainer cassandra =
            new CassandraContainer("cassandra:3.11.2")
                    .withInitScript("initial.cql")
                    .withExposedPorts(9042);

    @Test
    void test() {
        assertThat(cassandra.isRunning()).isTrue();

        CqlSession cqlSession =
                CqlSession.builder()
                        .addContactPoint(cassandra.getContactPoint())
                        .withLocalDatacenter(cassandra.getLocalDatacenter())
                        .build();

        var template = new CassandraTemplate(cqlSession);
        var selected = template.select("SELECT * from sample.t_sample", Sample.class);
        log.info("selected: {}", selected);
    }
}
