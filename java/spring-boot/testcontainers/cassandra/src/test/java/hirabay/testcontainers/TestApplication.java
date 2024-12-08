package hirabay.testcontainers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.cassandra.CassandraContainer;

public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.from(MainApplication::main) // main配下のメインクラスを指定
                .with(TestContainersConfiguration.class) // 先ほど作成したConfigurationクラスを指定
                .run(args);
    }
}
