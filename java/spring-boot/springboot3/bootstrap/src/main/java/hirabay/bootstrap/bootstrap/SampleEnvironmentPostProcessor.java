package hirabay.bootstrap.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class SampleEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.print("postProcessEnvironment");
        System.out.println(environment.getPropertySources());

//        environment.getPropertySources().addLast();
    }
}
