package hirabay.webmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class RequestParamArgumentResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper objectMapper;

    // Controller(RestController)の引数ごとに実行される
    // このメソッドの戻り値がtrueの場合だけ、resolveArgumentが実行される
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        parameter.getParameterAnnotation(<クラス>.class)
        var supportsParameter = parameter.getParameterType().getPackageName().startsWith("hirabay");
        log.info("parameter.getParameterType().getPackageName(): {}", parameter.getParameterType().getPackageName());
        log.info("supportsParameter: {}", supportsParameter);
        return supportsParameter;
    }

    // 引数を解決するメインメソッド
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        var clazz = parameter.getParameterType();
        var paramIterator = webRequest.getParameterNames();
        var paramMap = new HashMap<String, String>();
        while (paramIterator.hasNext()) {
            var paramName = paramIterator.next();
            var paramValue = webRequest.getParameter(paramName);
            paramMap.put(paramName, paramValue);
        }

        var paramJson = objectMapper.writeValueAsString(paramMap);
        return objectMapper.readValue(paramJson, clazz);
    }
}
