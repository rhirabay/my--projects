package rhirabay.todolist.presentation

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import rhirabay.todolist.domain.entity.TodoEntity
import rhirabay.todolist.presentation.resource.AddTodoRequest
import rhirabay.todolist.usecase.AddTodoUsecase
import rhirabay.todolist.usecase.SearchTodoUsecase

@RestController
@RequestMapping("/api")
class ApiController (private val searchTodoUsecase: SearchTodoUsecase,
                     private val addTodoUsecase: AddTodoUsecase) {
    companion object {
        private val log = LoggerFactory.getLogger(ApiController::class.java)
    }

    @GetMapping("/todo/{id}")
    fun todo(@PathVariable id: String): TodoEntity? {
        var todo = searchTodoUsecase.search(id)

        log.info("todo: {}", todo?.id)

        return todo
    }

    @GetMapping("/todo")
    fun todo(): List<TodoEntity> {
        return searchTodoUsecase.searchAll();
    }

    @PostMapping("/todo")
    fun add(@RequestBody addTodoRequest: AddTodoRequest) : TodoEntity {
        return addTodoUsecase.add(addTodoRequest.title)
    }
}