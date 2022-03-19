package rhirabay.todolist.usecase

import org.springframework.stereotype.Component
import rhirabay.todolist.domain.entity.TodoEntity
import rhirabay.todolist.domain.repository.TodoListRepository

@Component
class SearchTodoUsecase (private val todoListRepository: TodoListRepository) {
    fun search(id: String) : TodoEntity? {
        return todoListRepository.findById(id)
                .orElse(null)
    }

    fun searchAll() : List<TodoEntity> {
        return todoListRepository.findAll();
    }
}