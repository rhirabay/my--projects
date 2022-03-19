package rhirabay.todolist.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rhirabay.todolist.domain.entity.TodoEntity

@Repository
interface TodoListRepository : JpaRepository<TodoEntity, String> {
}