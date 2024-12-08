package hirabay.mybatis.mapper;

import hirabay.mybatis.entity.TodoList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoListMapper {
    List<TodoList> findAll();
}
