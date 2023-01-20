package com.example.demo;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;

@Validated
@RestController
public class SampleController {
    @PostConstruct
    void setup() {
        // something to do
    }

    @GetMapping("/sample")
    public String sample(@RequestParam @Max(10) int num) {
        return "ok";
    }
}
