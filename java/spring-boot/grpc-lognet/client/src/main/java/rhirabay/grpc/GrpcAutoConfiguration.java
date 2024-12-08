package rhirabay.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rhirabay.grpc.sample.GreetGrpc;

@Configuration
public class GrpcAutoConfiguration {
    @Bean
    ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
    }

    @Bean
    GreetGrpc.GreetBlockingStub greetBlockingStub(ManagedChannel managedChannel) {
        return GreetGrpc.newBlockingStub(managedChannel);
    }
}
