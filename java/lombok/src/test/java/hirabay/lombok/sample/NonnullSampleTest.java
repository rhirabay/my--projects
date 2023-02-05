package hirabay.lombok.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonnullSampleTest {
    @Test
    void null_case() {
        new NonnullSample().execute(null);
        // java.lang.NullPointerException: obj is marked non-null but is null
    }

    @Test
    void field() {
        new NonNullModel(null);
        // java.lang.NullPointerException: id is marked non-null but is null
    }
}