package rhirabay.redis.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class RedisClientTest {
    // テストコンテナを生成（裏でDockerコンテナが起動する）
    @Container
    public static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);

    private RedisClient redisClient;

    @BeforeEach
    void setup() {
        var redisConfiguration = new RedisStandaloneConfiguration(redis.getHost(), redis.getMappedPort(6379));
        var jedisConnectionFactory = new JedisConnectionFactory(redisConfiguration);
        jedisConnectionFactory.afterPropertiesSet();

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.afterPropertiesSet();

        redisClient = new RedisClient(redisTemplate);
    }

    @Test
    void test() {
        var value1 = redisClient.get("sample_key");
        assertThat(value1).isNull();

        redisClient.set("sample_key", "sample_value");
        var value2 = redisClient.get("sample_key");
        assertThat(value2).isEqualTo("sample_value");
    }
}