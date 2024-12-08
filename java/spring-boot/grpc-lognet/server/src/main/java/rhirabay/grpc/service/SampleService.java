package rhirabay.grpc.service;

import io.grpc.stub.StreamObserver;
import io.micrometer.core.annotation.Timed;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;
import rhirabay.grpc.sample.GreetResponse;

// @GRpcServiceを付与すると、grpcのserviceとして認識される
@Slf4j
@GRpcService
// @GRpcService(interceptors = SampleInterceptor.class)
public class SampleService extends GreetGrpc.GreetImplBase {
    @Override
    @Timed(percentiles = {0.5, 0.99})
    @SneakyThrows
    public void greeting(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        log.info("thread: {}", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        GreetResponse response =
                GreetResponse.newBuilder().setMessage("Hello, " + request.getName() + ".").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
