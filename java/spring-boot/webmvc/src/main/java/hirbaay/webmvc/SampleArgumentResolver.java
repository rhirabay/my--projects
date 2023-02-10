package hirbaay.webmvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SampleArgumentResolver implements HandlerMethodArgumentResolver {
    // Controller(RestController)の引数ごとに実行される
    // このメソッドの戻り値がtrueの場合だけ、resolveArgumentが実行される
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        parameter.getParameterAnnotation(<クラス>.class)
        return parameter.getParameterType().equals(SampleModel.class);
    }

    // 引数を解決するメインメソッド
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        var name = webRequest.getHeader("key");
        // 引数で受け取りたいクラスを返す
        return new SampleModel(name);
    }
}
