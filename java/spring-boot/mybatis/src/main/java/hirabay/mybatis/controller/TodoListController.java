package hirabay.mybatis.controller;

import hirabay.mybatis.mapper.TodoListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListMapper todoListMapper;

    @GetMapping("/todoList")
    public Object todoList() {
        return todoListMapper.findAll();
    }
}
