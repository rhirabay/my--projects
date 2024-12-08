package hirabay.webmvc.disableenable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

@DisabledIfEnvironmentVariable(named = "PR", matches = "true")
public class SampleTest {
    @Test
    @EnabledIfSystemProperty(named = "custom-props", matches = "test")
    void test1() {
        System.out.println("test1");
    }

    @Test
    void test2() {
        System.out.println("test2");
    }
}
