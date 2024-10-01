package hirabay.mybatis.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {
    private String title;
    private String id;
    private String updatedBy;
}
