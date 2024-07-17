package hirabay.webmvc.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Redirect307Controller {
    @GetMapping("/top")
    public String top() {
        return "top";
    }

    @PostMapping("/search")
    public String search() {
        return "searchResult";
    }

    @PostMapping("/delete")
    public void search(HttpServletResponse response) {
        response.setStatus(307);
        response.setHeader("Location", "/search");
    }
}
