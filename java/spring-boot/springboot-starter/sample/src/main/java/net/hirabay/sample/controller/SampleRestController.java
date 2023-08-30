package net.hirabay.sample.controller;

import lombok.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleRestController {
    @GetMapping("/api/sample")
    public SampleResponse sample() {
        return new SampleResponse("Hello, world.");
    }

    @Value
    public static class SampleResponse {
        private String message;
    }

    // $ curl -X GET http://localhost:8080/get
    // get endpoint
    @GetMapping("/get")
    public String getEndpoint() {
        return "get endpoint";
    }

    // $ curl -X POST http://localhost:8080/post
    // post endpoint
    @PostMapping("/post")
    public String postEndpoint() {
        return "post endpoint";
    }

    // $ curl -X DELETE http://localhost:8080/delete
    // delete endpoint
    @DeleteMapping("/delete")
    public String deleteEndpoint() {
        return "delete endpoint";
    }


}
