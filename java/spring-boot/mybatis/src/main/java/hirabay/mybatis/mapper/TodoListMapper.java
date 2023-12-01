package hirabay.mybatis.mapper;

import hirabay.mybatis.entity.TodoList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoListMapper {
    List<TodoList> findAll();
}
