package hirabay.junit5.cheatsheet

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any

@ExtendWith(MockKExtension::class)
internal class ParentComponentTest {
    // テスト対象のクラス。「@InjectMockKs」を付けると自動で依存クラスを注入してくれる
    @InjectMockKs
    lateinit var parentComponent: ParentComponent

    // テスト対象クラスの依存クラス。「@MockK」を付けるとMockオブジェクトを生成してくれる
    @MockK
    lateinit var childComponent: ChildComponent

    @Nested
    inner class GreetingTest {
        @Test
        fun test() {
            // 依存Mockの動作を定義
            every {
                childComponent.greeting(any())
            } returns "Dummy"

            val actual = parentComponent.greeting("hirabay")
            val expected = "Dummy"

            assertThat(actual).isEqualTo(expected)
            // 呼出され方の検証
            verify {
                childComponent.greeting(any())
            }
            // 呼出され方の検証（exactlyで呼び出し回数まで検証できる）
            verify(exactly = 1) {
                childComponent.greeting(any())
            }
        }
    }

    @Nested
    inner class GreetingSuspendTest {
        @Test
        fun test() {
            coEvery {
                childComponent.greetingSuspend(any())
            } returns "dummy"

            val actual = runBlocking {
                parentComponent.greetingSuspend("hirabay")
            }
            val expected = "dummy"

            assertThat(actual).isEqualTo(expected)

            coVerify {
                childComponent.greetingSuspend(any())
            }
        }
    }
}