package rhirabay.redis.infrastructure;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.embedded.RedisServer;

import static org.assertj.core.api.Assertions.assertThat;

class RedisClientTest {
    private static RedisServer redisServer = null;

    private RedisClient redisClient;

    @BeforeEach
    void init() {
        // 組み込みRedisに接続するRedisTemplateを生成する
        var connFactory = new LettuceConnectionFactory("localhost", 6379);
        connFactory.afterPropertiesSet();

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFactory);
        redisTemplate.afterPropertiesSet();

        redisClient = new RedisClient(redisTemplate);
    }

    @BeforeAll
    public static void setup() {
        if (redisServer == null) {
            // 組み込みRedisを起動する
            redisServer = new RedisServer(6379);
            redisServer.start();
        }
    }

    @AfterAll
    public static void teardown() {
        if (redisServer != null) {
            // 組み込みRedisを停止する
            redisServer.stop();
            redisServer = null;
        }
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