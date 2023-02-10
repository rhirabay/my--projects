package hirabay.lombok.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuilderSampleTest {
    @Test
    void test() {
        BuilderSample.builder()
                .id("dummy_id")
                .name("dummy_name")
                .build();
    }
}