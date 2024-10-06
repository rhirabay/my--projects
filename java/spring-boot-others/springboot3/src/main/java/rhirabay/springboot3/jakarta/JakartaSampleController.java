package rhirabay.springboot3.jakarta;

import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JakartaSampleController {
    @GetMapping("/jakarta/**")
    public String test(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
