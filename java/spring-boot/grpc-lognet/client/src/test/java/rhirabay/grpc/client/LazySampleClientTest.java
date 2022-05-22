package rhirabay.grpc.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LazySampleClientTest {
    @InjectMocks
    SampleClient sampleClient;

    @Mock
    GreetGrpc.GreetBlockingStub greetBlockingStub;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test() {
        var mockedResponse = GreetResponse.newBuilder()
                .setMessage("Hello.")
                .build();
        when(greetBlockingStub.greeting(any())).thenReturn(mockedResponse);
        var actual = sampleClient.greeting("ryo");
        var expected = "Hello.";

        assertThat(actual).isEqualTo(expected);
    }
}