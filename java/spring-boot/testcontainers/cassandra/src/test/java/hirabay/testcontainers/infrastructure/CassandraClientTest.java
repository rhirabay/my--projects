package hirabay.testcontainers.infrastructure;

import com.datastax.oss.driver.api.core.CqlSession;
import hirabay.testcontainers.domain.Sample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Testcontainers
class CassandraClientTest {
    @Container
    public static CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2")
            .withInitScript("initial.cql");

    private CassandraClient cassandraClient;

    @BeforeEach
    void setup() {
        // 起動したコンテナの情報を元にsessionを生成
        CqlSession cqlSession = CqlSession.builder()
                .addContactPoint(cassandra.getContactPoint())
                .withLocalDatacenter(cassandra.getLocalDatacenter())
                .build();

        var template = new CassandraTemplate(cqlSession);
        cassandraClient = new CassandraClient(template);
    }

    @Test
    void test() {
        var actual = cassandraClient.findAll();
        var expected = new Sample();
        expected.setKey("key1");
        expected.setValue("value1");

        assertThat(actual).isEqualTo(Collections.singletonList(expected));
    }
}