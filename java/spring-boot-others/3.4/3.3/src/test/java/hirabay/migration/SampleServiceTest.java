package hirabay.migration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SampleServiceTest {
    @MockBean
    private SampleClient sampleClient;

    @Autowired
    private SampleService sampleService;

    @Test
    void test() {
        when(sampleClient.hello()).thenReturn("dummy message");

        sampleService.sample();
    }
}