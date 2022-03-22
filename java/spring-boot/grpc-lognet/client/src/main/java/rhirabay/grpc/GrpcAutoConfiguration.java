package rhirabay.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rhirabay.grpc.sample.GreetGrpc;

@Slf4j
@Configuration
@EnableConfigurationProperties(GrpcProperties.class)
public class GrpcAutoConfiguration {

    @Bean
    ManagedChannel managedChannel(GrpcProperties grpcProperties) {
        var clientProps = grpcProperties.getClient().get("greeting");
        log.info("client props: {}", clientProps);
        return ManagedChannelBuilder.forAddress(clientProps.getHost(), clientProps.getPort())
                .usePlaintext()
                .build();
    }

    @Bean
    GreetGrpc.GreetBlockingStub greetBlockingStub(ManagedChannel managedChannel) {
        return GreetGrpc.newBlockingStub(managedChannel);
    }
}
