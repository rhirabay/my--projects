package rhirabay.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;
import rhirabay.grpc.sample.GreetResponse;

import java.util.function.Function;

@TestConfiguration
public class SampleClientTestConfiguration {

    /**
     * Mock化して使うことを想定
     */
    @Bean
    Function<String, String> serviceImpl() {
        return str -> "dummy message";
    }

    @GRpcService
    @RequiredArgsConstructor
    public static class DummyService extends GreetGrpc.GreetImplBase {
        private final Function<String, String> serviceImpl;

        @Override
        public void greeting(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
            GreetResponse response = GreetResponse.newBuilder()
                    .setMessage(serviceImpl.apply(request.getName()))
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
