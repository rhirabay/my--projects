package rhirabay.grpc.service;

import io.grpc.stub.StreamObserver;
import io.micrometer.core.annotation.Timed;
import org.lognet.springboot.grpc.GRpcService;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;
import rhirabay.grpc.sample.GreetResponse;

// @GRpcServiceを付与すると、grpcのserviceとして認識される
@GRpcService(interceptors = SampleInterceptor.class)
public class SampleService extends GreetGrpc.GreetImplBase {
    @Override
    @Timed(percentiles = { 0.5, 0.99 })
    public void greeting(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        GreetResponse response = GreetResponse.newBuilder()
                .setMessage("Hello, " + request.getName() + ".")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
