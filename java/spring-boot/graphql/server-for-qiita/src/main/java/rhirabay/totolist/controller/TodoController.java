package rhirabay.totolist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rhirabay.totolist.model.Project;
import rhirabay.totolist.model.Todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class TodoController {
    private static List<Todo> TODO_LIST = new ArrayList<>(Arrays.asList(
            new Todo("1", "project1-task1"),
            new Todo("2", "project1-task2"),
            new Todo("3", "project1-task3"),
            new Todo("4", "project2-task1"),
            new Todo("5", "project2-task2"),
            new Todo("6", "project2-task3")
    ));

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

    private static List<Project> PROJECT_LIST = Arrays.asList(
            new Project("1", "project1"),
            new Project("2", "project2")
    );

    @QueryMapping
    Flux<Project> projects() {
        return Flux.fromStream(PROJECT_LIST.stream());
    }

    @SchemaMapping(typeName = "Project")
    Flux<Todo> todoList(Project project) {
        return Flux.fromStream(
                TODO_LIST.stream()
                        .filter(todo -> todo.getTitle().startsWith(project.getName()))
        );
    }

    @MutationMapping
    Mono<Todo> addTodo(@Argument String title) {
        var id = UUID.randomUUID().toString();
        var todo = new Todo(id, title);
        TODO_LIST.add(todo);
        return Mono.just(todo);
    }
}
