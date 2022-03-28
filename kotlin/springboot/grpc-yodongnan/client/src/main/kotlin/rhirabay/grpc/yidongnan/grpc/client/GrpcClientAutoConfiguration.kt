package rhirabay.grpc.yidongnan.grpc.client

import net.devh.boot.grpc.client.inject.GrpcClient
import net.devh.boot.grpc.client.inject.GrpcClientBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rhirabay.grpc.sample.GreetGrpc


@Configuration
@GrpcClientBean(
        clazz = GreetGrpc.GreetBlockingStub::class,
        beanName = "greetStub",
        client = GrpcClient("greet")
)
class GrpcClientAutoConfiguration {
    @Bean
    fun greetClient(greetStub: GreetGrpc.GreetBlockingStub): GreetClient {
        return GreetClient(greetStub)
    }
}