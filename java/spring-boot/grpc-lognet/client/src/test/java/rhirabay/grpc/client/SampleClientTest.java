package rhirabay.grpc.client;


import io.grpc.HandlerRegistry;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.util.MutableHandlerRegistry;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import rhirabay.grpc.sample.GreetGrpc;
import rhirabay.grpc.sample.GreetRequest;
import rhirabay.grpc.sample.GreetResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class SampleClientTest {
    private SampleClient sampleClient;

    static String UNIQUE_SERVER_NAME = "server^name";

    private static MutableHandlerRegistry serviceRegistry = new MutableHandlerRegistry();
    private static Server inProcessServer = InProcessServerBuilder
            .forName(UNIQUE_SERVER_NAME)
            .fallbackHandlerRegistry(serviceRegistry)
            .directExecutor()
            .build();

    @Spy
    private GreetGrpcMock greetGrpcMock = new GreetGrpcMock();

    @BeforeAll
    @SneakyThrows
    static void startServer() {
        inProcessServer.start();
    }

    @AfterAll
    static void shutdownServer() {
        inProcessServer.shutdownNow();
    }

    @BeforeEach
    void setup() {
        var inProcessChannel = InProcessChannelBuilder
                .forName(UNIQUE_SERVER_NAME)
                .directExecutor()
                .build();

        serviceRegistry.addService(greetGrpcMock);
        var stub = GreetGrpc.newBlockingStub(inProcessChannel);
        sampleClient = new SampleClient(stub);
    }

    @Test
    void test() {
        // gRPCサービスの動作を定義
        doAnswer(invocation -> {
            // リクエストを検証する（ここで実装するのもどうかと思うが…）
            var request = (GreetRequest)(invocation.getArgument(0));
            assertThat(request.getName()).isEqualTo("test");

            var responseObserver = (StreamObserver<GreetResponse>)(invocation.getArgument(1));
            GreetResponse response = GreetResponse.newBuilder()
                    .setMessage("Hello, client test.")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

            return null;
        }).when(greetGrpcMock).greeting(any(), any());

        // テスト対象のメソッド呼び出しと検証
        var actual = sampleClient.greeting("test");
        var expected = "Hello, client test.";
        assertThat(actual).isEqualTo(expected);
    }

    // spy化のためにgRPCサービスクラスを定義
    static class GreetGrpcMock extends GreetGrpc.GreetImplBase {}
}