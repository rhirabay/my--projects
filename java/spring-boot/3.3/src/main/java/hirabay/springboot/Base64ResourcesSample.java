package hirabay.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class Base64ResourcesSample {
    @Value("${base64.resources.sample}")
    private String base64ResourcesSample;

    @GetMapping("/base64-resources-sample")
    public String getBase64ResourcesSample() throws Exception {
        var resource = new ApplicationResourceLoader().getResource(this.base64ResourcesSample);

        return resource.getContentAsString(StandardCharsets.UTF_8);
    }
}
