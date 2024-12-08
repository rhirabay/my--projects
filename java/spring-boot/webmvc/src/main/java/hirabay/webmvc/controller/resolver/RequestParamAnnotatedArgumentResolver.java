package hirabay.webmvc.controller.resolver;

import java.lang.reflect.Field;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class RequestParamAnnotatedArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // パッケージで対象を特定
        var supportsParameter = parameter.getParameterType().getPackageName().startsWith("hirabay");
        return supportsParameter;
    }

    // 引数を解決するメインメソッド
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        var clazz = parameter.getParameterType();
        var constructor = clazz.getConstructor();
        var resolvedArgument = constructor.newInstance();

        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            final ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
            if (paramNameAnnotation != null && !ObjectUtils.isEmpty(paramNameAnnotation.value())) {
                var value = paramNameAnnotation.value();
                field.set(resolvedArgument, webRequest.getParameter(value));
            } else {
                field.set(resolvedArgument, webRequest.getParameter(field.getName()));
            }
        }

        return resolvedArgument;
    }
}
