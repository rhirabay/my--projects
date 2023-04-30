package hirabay.testcontainers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.CassandraContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {

    @Bean
    @ServiceConnection
    public CassandraContainer<?> cassandraContainer() {
        return new CassandraContainer<>("cassandra:3.11.2")
                .withInitScript("initial.cql") // 初期クエリ
                .withExposedPorts(9042);
    }

}