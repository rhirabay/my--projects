package hirabay.junit5.cheatsheet

import org.springframework.stereotype.Component

@Component
class ParentComponent(private val childComponent: ChildComponent) {
    fun greeting(name: String): String {
        return childComponent.greeting(name)
    }

    suspend fun greetingSuspend(name: String): String {
        return childComponent.greetingSuspend(name)
    }
}