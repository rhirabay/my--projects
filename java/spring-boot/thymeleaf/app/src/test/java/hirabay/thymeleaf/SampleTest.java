package hirabay.thymeleaf;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Test;

public class SampleTest {
    @Value
    @Builder
    public static class SampleModel {
        @NonNull
        String name;
        int age;
    }

    @Test
    void test() {
        var model = SampleModel.builder()
                .name("hirabay")
                .build();

        System.out.println(model);
    }
}
