package hirabay.lombok.sample;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

// builderパターンでオブジェクト生成したいクラスに付与
@Builder
@Value
public class BuilderSample {
    // 必須で値を設定すべきフィールドにはNunNullを付与
    @NonNull
    private String id;
    private String name;
}
