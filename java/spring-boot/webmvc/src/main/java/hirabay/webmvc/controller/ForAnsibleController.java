package hirabay.webmvc.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ansible")
@RestController
public class ForAnsibleController {
    @GetMapping("/ok")
    public Object ok() {
        return Message.builder()
                .message("ok")
                .build();
    }

    @GetMapping("/ng")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message ng() {
        return Message.builder()
                .message("ng")
                .build();
    }

    @Data
    @Builder
    public static class Message {
        private String message;
    }
}
