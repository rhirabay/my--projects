package hirabay.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import hirabay.testcontainers.domain.Sample;
import hirabay.testcontainers.infrastructure.CassandraClient;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ImportTestcontainers(CassandraTestContainers.class)
class ApplicationTest {
    @Autowired private CassandraClient cassandraClient;

    // テストコンテナを生成（裏でDockerコンテナが起動する）
    //    @Container
    //    @ServiceConnection
    //    public static CassandraContainer cassandra = new CassandraContainer("cassandra:3.11.2")
    //            .withInitScript("initial.cql"); // 初期クエリ

    // propertiesを更新
    //    @DynamicPropertySource
    //    static void properties(DynamicPropertyRegistry registry) {
    //        registry.add("spring.cassandra.keyspace-name", () -> "sample");
    //        var contactPoint = "%s:%d".formatted(cassandra.getContactPoint().getHostName(),
    // cassandra.getContactPoint().getPort());
    //        registry.add("spring.cassandra.contact-points", () -> contactPoint);
    //        registry.add("spring.cassandra.local-datacenter", () ->
    // cassandra.getLocalDatacenter());
    //    }

    @Test
    void test() {
        var actual = cassandraClient.findAll();
        var expected = new Sample();
        expected.setKey("key1");
        expected.setValue("value1");

        assertThat(actual).isEqualTo(Collections.singletonList(expected));
    }
}
