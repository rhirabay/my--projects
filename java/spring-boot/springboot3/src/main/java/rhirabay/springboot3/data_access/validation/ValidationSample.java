package rhirabay.springboot3.data_access.validation;

import jakarta.validation.constraints.Max;
// javaxのアノテーションではバリデートが効かなかった...
//import javax.validation.constraints.Max;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ValidationSample {
    @GetMapping("/data-access/validation")
    public String sample(@RequestParam @Max(100) int num) {
        System.out.println(num);
        return "ok";
    }
}
