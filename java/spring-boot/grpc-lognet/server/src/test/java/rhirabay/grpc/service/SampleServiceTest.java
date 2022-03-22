package rhirabay.grpc.service;

import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rhirabay.grpc.sample.GreetRequest;
import rhirabay.grpc.sample.GreetResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SampleServiceTest {
    @InjectMocks
    private SampleService sampleService;

    @Mock
    private StreamObserver<GreetResponse> responseObserver;

    @Test
    void mock_response() {
        doNothing().when(responseObserver).onNext(any());
        doNothing().when(responseObserver).onCompleted();

        var request = GreetRequest.newBuilder()
                .setName("Ryo")
                .build();

        sampleService.greeting(request, responseObserver);

        var expected = GreetResponse.newBuilder()
                .setMessage("Hello, Ryo.")
                .build();
        verify(responseObserver).onNext(expected);
    }
}