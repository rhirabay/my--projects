package rhirabay.totolist.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import rhirabay.totolist.model.Todo;

import java.util.Arrays;
import java.util.List;

@Controller
public class TodoController {
    private static List<Todo> TODO_LIST = Arrays.asList(
            new Todo("1", "task1"),
            new Todo("2", "task2")
    );

    @QueryMapping
    Todo todo(@Argument String id) {
        return TODO_LIST.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @QueryMapping
    Flux<Todo> todoList() {
        return Flux.fromStream(TODO_LIST.stream());
    }
}
