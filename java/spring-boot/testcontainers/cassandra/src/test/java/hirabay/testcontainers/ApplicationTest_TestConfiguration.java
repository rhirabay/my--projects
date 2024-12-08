package hirabay.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import hirabay.testcontainers.domain.Sample;
import hirabay.testcontainers.infrastructure.CassandraClient;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = {MainApplication.class, TestContainersConfiguration.class})
class ApplicationTest_TestConfiguration {
    @Autowired private CassandraClient cassandraClient;

    @Test
    void test() {
        var actual = cassandraClient.findAll();
        var expected = new Sample();
        expected.setKey("key1");
        expected.setValue("value1");

        assertThat(actual).isEqualTo(Collections.singletonList(expected));
    }
}
