package rhirabay.grpc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import rhirabay.grpc.client.SampleClientTestConfiguration;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GrpcApplication.class, SampleClientTestConfiguration.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
		"grpc.port=6565",
		"rhirabay.grpc.client.greeting.host=localhost",
		"rhirabay.grpc.client.greeting.port=${grpc.port}",
		}
)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class GrpcApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private Function<String, String> serviceImpl;

	@Test
	void test() {
		when(serviceImpl.apply(any())).thenReturn("dummy message");

		webTestClient.get()
				.uri("/greeting")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("dummy message");
	}

}
