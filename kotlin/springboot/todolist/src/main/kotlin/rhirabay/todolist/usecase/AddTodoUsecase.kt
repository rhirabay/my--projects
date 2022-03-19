package rhirabay.todolist.usecase

import org.springframework.stereotype.Component
import rhirabay.todolist.domain.TodoId
import rhirabay.todolist.domain.entity.TodoEntity
import rhirabay.todolist.domain.repository.TodoListRepository
import java.util.*

@Component
class AddTodoUsecase (private val todoListRepository: TodoListRepository) {
    fun add (title: String) : TodoEntity {
        val entity = TodoEntity(TodoId().value, title)

        todoListRepository.save(entity)

        return entity
    }
}