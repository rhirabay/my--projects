package hirabay.library;

import hirabay.library.plain.HttpClientCustomizer;
import hirabay.library.plain.MathUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

@RestController
public class JavaLibSampleController {
    @GetMapping("/java/lib/sample")
    public int javaLibSample(
            @RequestParam int n1,
            @RequestParam int n2
    ) {
        return MathUtils.plus(n1, n2);
    }
}
