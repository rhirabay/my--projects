package hirabay.beans;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
public class SampleAutoConfiguration {
//    public SampleAutoConfiguration(GenericApplicationContext context) {
//        var restTemplate1 = new RestTemplateBuilder()
//                .rootUri("http://localhost:8080")
//                        .build();
//        var restTemplate2 = new RestTemplateBuilder()
//                .rootUri("http://localhost:80")
//                .build();
//
//        context.registerBean("restTemplate1", RestTemplate.class, () -> restTemplate1);
//        context.registerBean("restTemplate2", RestTemplate.class, () -> restTemplate2);
//    }

    public SampleAutoConfiguration(GenericApplicationContext context, RestTemplateProperties properties) {
        properties.getRestTemplates().keySet().forEach(name -> {
            RestTemplateProperties.RestTemplateProperty property = properties.getRestTemplates().get("name");
            var restTemplate = new RestTemplateBuilder()
                    .rootUri(property.getRootUri())
                    .build();
            context.registerBean(name + "RestTemplate", RestTemplate.class, () -> restTemplate);
        });
    }
}
