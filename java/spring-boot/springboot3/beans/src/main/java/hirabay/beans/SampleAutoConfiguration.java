package hirabay.beans;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SampleAutoConfiguration {
    public SampleAutoConfiguration(GenericApplicationContext context) {
        var restTemplate1 = new RestTemplateBuilder()
                .rootUri("http://localhost:8080")
                        .build();
        var restTemplate2 = new RestTemplateBuilder()
                .rootUri("http://localhost:80")
                .build();

        context.registerBean("restTemplate1", RestTemplate.class, () -> restTemplate1);
        context.registerBean("restTemplate2", RestTemplate.class, () -> restTemplate2);
    }
}
