package rhirabay.grpc.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;

@Component
@RequiredArgsConstructor
public class TlsSampleClient {
    private final GreetGrpc.GreetBlockingStub tlsGreetBlockingStub;

    public String greeting(String name){
        var request = GreetRequest.newBuilder().setName(name).build();
        var response = this.tlsGreetBlockingStub.greeting(request);
        return response.getMessage();
    }
}
