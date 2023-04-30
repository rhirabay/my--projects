package hirabay.testcontainers;

import hirabay.testcontainers.domain.Sample;
import hirabay.testcontainers.infrastructure.CassandraClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class ApplicationTest {
    @Autowired
    private CassandraClient cassandraClient;


    // テストコンテナを生成（裏でDockerコンテナが起動する）
    @Container
    public static CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2")
            .withInitScript("initial.cql"); // 初期クエリ

    // propertiesを更新
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.cassandra.keyspace-name", () -> "sample");
        var contactPoint = "%s:%d".formatted(cassandra.getContactPoint().getHostName(), cassandra.getContactPoint().getPort());
        registry.add("spring.cassandra.contact-points", () -> contactPoint);
        registry.add("spring.cassandra.local-datacenter", () -> cassandra.getLocalDatacenter());
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
