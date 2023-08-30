package hirabay.library.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MathUtilsAutoConfiguration {
    @Bean
    public MathUtils mathUtils() {
        return new MathUtils();
    }
}
