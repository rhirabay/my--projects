package rhirabay.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
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

    @Bean
    @SneakyThrows
    ManagedChannel tlsManagedChannel(GrpcProperties grpcProperties) {
        var clientProps = grpcProperties.getClient().get("tls-greeting");
        log.info("client props: {}", clientProps);
        var channelBuilder = ManagedChannelBuilder.forAddress(clientProps.getHost(), clientProps.getPort());
        var certChain = new FileUrlResource("../cert/server.pem");
        var sslContext = GrpcSslContexts.forClient().trustManager(certChain.getInputStream()).build();
        return ((NettyChannelBuilder)channelBuilder)
                .useTransportSecurity()
                .sslContext(sslContext)
                .build();
    }

    @Bean
    GreetGrpc.GreetBlockingStub tlsGreetBlockingStub(ManagedChannel tlsManagedChannel) {
        return GreetGrpc.newBlockingStub(tlsManagedChannel);
    }
}
