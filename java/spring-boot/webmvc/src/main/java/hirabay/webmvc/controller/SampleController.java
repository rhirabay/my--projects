package hirabay.webmvc.controller;

import hirabay.webmvc.domain.SampleModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/sample/plain")
    public String plain(HttpServletRequest request) {
        return request.getHeader("key");
    }

    @GetMapping("/sample/resolver")
    public String resolver(
            SampleModel sampleModel
    ) {
        return sampleModel.getKey();
    }
}
