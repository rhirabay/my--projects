package hirabay.migration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.when;

@SpringBootTest
class SampleServiceTest {
    @MockitoBean
    private SampleClient sampleClient;

    @Autowired
    private SampleService sampleService;

    @Test
    void test() {
        when(sampleClient.hello()).thenReturn("dummy message");

        sampleService.sample();
    }
}