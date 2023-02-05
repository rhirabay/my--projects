package hirabay.lombok.sample;

import lombok.NonNull;
import lombok.Value;

public class NonnullSample {
    // nullチェックを行いたい引数に付与する
    public void execute(@NonNull Object obj) {
        // something to do
    }
}

@Value
class NonNullModel {
    // non nullを確約したいフィールドに付与する
    @NonNull
    private String id;
}
