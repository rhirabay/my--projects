package rhirabay.todolist.usecase

import rhirabay.todolist.domain.entity.TodoEntity
import rhirabay.todolist.domain.repository.TodoListRepository

class ModifyTodoUsecase (private val todoListRepository: TodoListRepository) {
    /**
     * Todoを編集する
     * 編集できたら、編集後のEntityを返却する
     * 編集に失敗したらnullを返却する
     */
    fun modify(modified: TodoEntity, id: String) : TodoEntity? {
        val todo = todoListRepository.findById(id).orElse(null)

        return if (todo != null) {
            val newEntity = TodoEntity(id, modified.title)
            todoListRepository.save(newEntity)
            newEntity
        } else {
            null
        }
    }
}