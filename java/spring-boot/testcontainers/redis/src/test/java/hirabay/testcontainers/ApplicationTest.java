package hirabay.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import hirabay.testcontainers.infrastructure.RedisClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
class ApplicationTest {
    @Autowired private RedisClient redisClient;

    // テストコンテナを生成（裏でDockerコンテナが起動する）
    @Container
    public static GenericContainer redis =
            new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
                    .withExposedPorts(6379);

    // propertiesを更新
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", () -> redis.getHost());
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Test
    void test() {
        redisClient.set("sample", "sample");
        var actual = redisClient.get("sample");
        var expected = "sample";

        assertThat(actual).isEqualTo(expected);
    }
}
