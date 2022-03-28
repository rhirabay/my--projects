package rhirabay.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import rhirabay.grpc.sample.GreetGrpc;

@Slf4j
@Configuration
@EnableConfigurationProperties(GrpcProperties.class)
public class GrpcTlsAutoConfiguration {
    @Bean
    @SneakyThrows
    ManagedChannel tlsManagedChannel(GrpcProperties grpcProperties) {
        var channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6566);
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
