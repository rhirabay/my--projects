package hirabay.junit5.cheatsheet

import io.mockk.every
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OtherTest {
    @Test
    fun staticMethod() {
        val mockedValue = LocalDate.of(2020, 8, 11)

        // staticメソッドをMock化
        mockkStatic(LocalDate::class)

        every {
            LocalDate.now()
        } returns mockedValue

        val actual = LocalDate.now()
        assertThat(actual).isEqualTo(mockedValue)

        // Mock化を解除
        unmockkStatic(LocalDate::class)
    }

    @Test
    fun constructor() {
        mockkConstructor(ChildComponent::class)
        // Mock動作を指定
        // 注意：コンストラクタの呼び出し前に実装すること！
        every {
            anyConstructed<ChildComponent>().greeting(any())
        } returns "dummy"

        val childComponent = ChildComponent()

        val actual = childComponent.greeting("hirabay")

        assertThat(actual).isEqualTo("dummy")

        verify {
            anyConstructed<ChildComponent>().greeting(any())
        }
    }
}