package hirabay.lombok.sample;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DataSampleTest {
    @Test
    void method() {
        var sample = new DataSample();
        sample.setId("dummy");
        sample.setName("dummy");
        sample.getId(); // dummy
        sample.getName(); // dummy
        sample.toString(); // DataSample(id=dummy, name=dummy)

        System.out.println(sample);
    }

    @Test
    void equals() {
        var sample1 = new DataSample();
        var sample2 = new DataSample();
        sample1.equals(sample2); // true

        System.out.println(sample1.equals(sample2));
    }
}