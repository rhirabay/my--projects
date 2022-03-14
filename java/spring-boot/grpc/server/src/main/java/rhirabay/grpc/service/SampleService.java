package rhirabay.grpc.service;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;
import rhirabay.grpc.sample.GreetResponse;

@GRpcService(interceptors = SampleInterceptor.class)
public class SampleService extends GreetGrpc.GreetImplBase {
    @Override
    public void greeting(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        GreetResponse response = GreetResponse.newBuilder()
                .setMessage("Hello, " + request.getName() + ".")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
