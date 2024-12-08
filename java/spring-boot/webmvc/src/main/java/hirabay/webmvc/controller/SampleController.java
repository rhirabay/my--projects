package hirabay.webmvc.controller;

import hirabay.webmvc.domain.SampleModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/", "/dummy"})
public class SampleController {
    // curl localhost:8080/sample/plain -H "key: value"
    // curl localhost:8080/dummy/sample/plain -H "key: value"
    @GetMapping("/sample/plain")
    public String plain(HttpServletRequest request) {
        return request.getHeader("key");
    }

    @GetMapping("/sample/resolver")
    public String resolver(SampleModel sampleModel) {
        return sampleModel.getKey();
    }
}
