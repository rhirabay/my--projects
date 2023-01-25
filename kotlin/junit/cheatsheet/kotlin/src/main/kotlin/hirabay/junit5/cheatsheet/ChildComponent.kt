package hirabay.junit5.cheatsheet

import org.springframework.stereotype.Component

@Component
class ChildComponent {
    fun greeting(name: String): String {
        return "Hello, ${name}."
    }

    suspend fun greetingSuspend(name: String): String {
        return "Hello, ${name}."
    }

}