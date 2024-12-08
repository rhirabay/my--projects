package hirabay.webmvc.controller;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/objectMapper")
public class ObjectMapperController {
    private RestTemplate restTemplate =
            new RestTemplateBuilder().rootUri("http://localhost:8080").build();

    @GetMapping("/client")
    public ClientResponse client() {
        return restTemplate.getForObject("/objectMapper/server", ClientResponse.class);
    }

    @GetMapping("/server")
    public ServerResponse server() {
        return new ServerResponse(Message.of("Hello, world."));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServerResponse {
        private Message message;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientResponse {
        private Message message;
    }

    @Value
    public static class Message {
        @JsonValue private String value;

        public static Message of(String value) {
            return new Message(value);
        }
    }
}
