package hirabay.mybatis.mapper;

import hirabay.mybatis.entity.TodoList;
import jakarta.annotation.Nullable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoListMapper {
    @Nullable
    List<TodoList> findAll();
}
