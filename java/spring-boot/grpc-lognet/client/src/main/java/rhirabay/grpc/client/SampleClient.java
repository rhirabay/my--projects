package rhirabay.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;

@Component
@RequiredArgsConstructor
public class SampleClient {
    private final GreetGrpc.GreetBlockingStub stub;

    public String greeting(String name){
        var request = GreetRequest.newBuilder().setName(name).build();
        var response = this.stub.greeting(request);
        return response.getMessage();
    }
}
