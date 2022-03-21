package rhirabay.grpc.client;

import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;
import rhirabay.grpc.sample.GreetResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SampleClientTest {
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