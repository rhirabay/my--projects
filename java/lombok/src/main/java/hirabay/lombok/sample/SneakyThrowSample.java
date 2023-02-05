package hirabay.lombok.sample;

import lombok.Lombok;
import lombok.SneakyThrows;

public class SneakyThrowSample {

    // 例外が発生する可能性のあるメソッドに付与する
    @SneakyThrows
    public void error() {
        throw new Exception();
    }

    public static class SampleException extends Exception {}
}
