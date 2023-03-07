package hirbaay.webmvc;

import hirbaay.webmvc.controller.SampleArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class HandlerMethodArgumentResolverConfiguration implements WebMvcConfigurer {
    // 実装したresolverを登録する
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SampleArgumentResolver());
    }
}
