package hirabay.springboot.restclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

class MigrateFromRestTemplateTest {
    @Test
    void test() {
RestTemplate restTemplate = new RestTemplateBuilder()
        .build();

RestClient restClient = RestClient.builder(restTemplate)
        .build();
    }
}
