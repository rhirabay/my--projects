package hirabay.lombok.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueSampleTest {
    @Test
    void test() {
        var sample = new ValueSample("dummy_id", "dummy_name");
        sample.getId(); // dummy_id
        sample.getName(); // dummy_name
        sample.toString(); // ValueSample(id=dummy_id, name=dummy_name)

        System.out.println(sample.toString());
    }

    @Test
    void equal() {
var sample1 = new ValueSample("dummy_id", "dummy_name");
var sample2 = new ValueSample("dummy_id", "dummy_name");
sample1.equals(sample2); // true

        System.out.println(sample1.equals(sample2));
    }
}