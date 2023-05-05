package hirabay.testcontainers;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;

public interface CassandraTestContainers {
    @Container
    @ServiceConnection
    CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2")
            .withInitScript("initial.cql"); // 初期クエリ
}
