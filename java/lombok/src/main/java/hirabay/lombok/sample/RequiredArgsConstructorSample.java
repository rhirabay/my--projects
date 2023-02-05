package hirabay.lombok.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
// コンストラクタを生成したいクラスに付与する
@RequiredArgsConstructor
public class RequiredArgsConstructorSample {
    private final ObjectMapper objectMapper;
}
