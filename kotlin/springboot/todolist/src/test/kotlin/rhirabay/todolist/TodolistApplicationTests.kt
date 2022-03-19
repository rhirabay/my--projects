package rhirabay.todolist

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(classes = [TodolistApplication::class],
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodolistApplicationTests {
	@Autowired
	lateinit var webTestClient: WebTestClient

	@Test
	fun `integration test`() {
		webTestClient.get()
				.uri("/api/todo")
				.exchange()
				.expectStatus().isOk
	}


}
