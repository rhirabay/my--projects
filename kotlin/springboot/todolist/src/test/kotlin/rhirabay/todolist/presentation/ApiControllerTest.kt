package rhirabay.todolist.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import rhirabay.todolist.domain.entity.TodoEntity
import rhirabay.todolist.usecase.AddTodoUsecase
import rhirabay.todolist.usecase.SearchTodoUsecase

@WebMvcTest
internal class ApiControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient
    @MockkBean
    lateinit var searchTodoUsecase: SearchTodoUsecase
    @MockkBean
    lateinit var addTodoUsecase: AddTodoUsecase

    @Test
    fun test() {
        val todo = TodoEntity("dummy_id", "dummy_title")
        every { searchTodoUsecase.search(any()) } returns todo

        webTestClient.get().uri("/api/todo/dummy_id")
                .exchange().expectStatus().isOk
                .expectBody()
                    .jsonPath("$.id").isEqualTo("dummy_id")
                    .jsonPath("$.title").isEqualTo("dummy_title")
    }
}