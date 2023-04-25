package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/sample")
    public Object cookie(HttpServletRequest request) {
        String nullVal = null;

        nullVal.equals("");

        return request.getCookies();
    }
}
