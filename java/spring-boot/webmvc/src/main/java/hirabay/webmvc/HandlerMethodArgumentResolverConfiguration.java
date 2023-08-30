package hirabay.webmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import hirabay.webmvc.controller.SampleArgumentResolver;
import hirabay.webmvc.controller.RequestParamArgumentResolver;
import hirabay.webmvc.controller.resolver.RequestParamAnnotatedArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class HandlerMethodArgumentResolverConfiguration implements WebMvcConfigurer {
    private final ObjectMapper objectMapper;

    // 実装したresolverを登録する
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SampleArgumentResolver());
//        argumentResolvers.add(new RequestParamArgumentResolver(objectMapper));
        argumentResolvers.add(new RequestParamAnnotatedArgumentResolver());
    }
}
