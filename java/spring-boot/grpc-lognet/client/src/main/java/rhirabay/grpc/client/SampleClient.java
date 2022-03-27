package rhirabay.grpc.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;

@Component
@RequiredArgsConstructor
public class SampleClient {
    private final GreetGrpc.GreetBlockingStub greetBlockingStub;

    public String greeting(String name) {
        var request = GreetRequest.newBuilder()
                .setName(name)
                .build();
        var response = this.greetBlockingStub.greeting(request);
        return response.getMessage();
    }
}
