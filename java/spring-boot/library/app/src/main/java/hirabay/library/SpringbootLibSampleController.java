package hirabay.library;

import hirabay.library.springboot.MathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpringbootLibSampleController {
    private final MathUtils mathUtils;

    @GetMapping("/java/lib/sample")
    public int javaLibSample(
            @RequestParam int n1,
            @RequestParam int n2
    ) {
        return mathUtils.plus(n1, n2);
    }
}
