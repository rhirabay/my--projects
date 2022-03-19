package rhirabay.todolist.domain.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TODO_LIST")
data class TodoEntity (
        @Id val id: String?,
        val title: String
)