package hirabay.testcontainers;

import hirabay.testcontainers.domain.Sample;
import hirabay.testcontainers.infrastructure.CassandraClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(classes = { MainApplication.class, TestContainersConfiguration.class })
class ApplicationTest_TestConfiguration {
    @Autowired
    private CassandraClient cassandraClient;

    @Test
    void test() {
        var actual = cassandraClient.findAll();
        var expected = new Sample();
        expected.setKey("key1");
        expected.setValue("value1");

        assertThat(actual).isEqualTo(Collections.singletonList(expected));
    }
}
