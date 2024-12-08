package rhirabay.grpc;

import static org.assertj.core.api.Assertions.assertThat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;

@SpringBootTest(
        classes = GrpcApplication.class,
        properties = "grpc.port=0" // ランダムポート
        )
@ActiveProfiles("test")
class GrpcApplicationTests {
    @LocalRunningGrpcPort private int runningPort;

    @Test
    void test() {
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress("localhost", runningPort).usePlaintext().build();

        var stub = GreetGrpc.newBlockingStub(channel);

        var request = GreetRequest.newBuilder().setName("Ryo").build();
        var actual = stub.greeting(request).getMessage();
        var expected = "Hello, Ryo.";
        assertThat(actual).isEqualTo(expected);
    }
}
