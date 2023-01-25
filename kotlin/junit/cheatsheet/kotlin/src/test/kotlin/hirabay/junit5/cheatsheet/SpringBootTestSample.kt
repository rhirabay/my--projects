package hirabay.junit5.cheatsheet

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SpringBootTestSample {
    @MockkBean
    lateinit var childComponent: ChildComponent

    @Autowired
    lateinit var parentComponent: ParentComponent

    @Test
    fun test() {
        every {
            childComponent.greeting(any())
        } returns "dummy"

        val actual = parentComponent.greeting("hirabay")
        val expected = "dummy"

        assertThat(actual).isEqualTo(expected)
    }
}