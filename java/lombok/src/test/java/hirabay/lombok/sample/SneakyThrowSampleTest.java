package hirabay.lombok.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SneakyThrowSampleTest {
    @Test
    void test() {
        new SneakyThrowSample().error();
    }
}