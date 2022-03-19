package rhirabay.todolist.domain

import java.util.*

class TodoId {
    val value = UUID.randomUUID().toString().split("-")[0]
}