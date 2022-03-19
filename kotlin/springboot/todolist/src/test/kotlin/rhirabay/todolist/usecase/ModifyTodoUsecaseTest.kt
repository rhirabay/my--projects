package rhirabay.todolist.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import rhirabay.todolist.domain.entity.TodoEntity
import rhirabay.todolist.domain.repository.TodoListRepository
import java.util.*

import org.assertj.core.api.Assertions.assertThat

@ExtendWith(MockKExtension::class)
internal class ModifyTodoUsecaseTest {
    @InjectMockKs
    lateinit var target: ModifyTodoUsecase
    @MockK
    lateinit var todoListRepository: TodoListRepository

    @Test
    fun todo_not_found() {
        every { todoListRepository.findById(any()) } returns Optional.empty()

        val entity = TodoEntity(null, "タイトル")
        assertThat(target.modify(entity, "dummy_id")).isNull()
    }

    @Test
    fun modify_success() {
        val existEntity = TodoEntity("dummy_id", "old title")
        val newEntity = TodoEntity("dummy_id", "new title")
        every { todoListRepository.findById(any()) } returns Optional.of(existEntity)
        every { todoListRepository.save(any()) } returns newEntity

        val entity = TodoEntity(null, "new title")
        val actual = target.modify(entity, "dummy_id")

        assertThat(actual).isEqualTo(newEntity)
        verify { todoListRepository.save(newEntity) }
    }
}