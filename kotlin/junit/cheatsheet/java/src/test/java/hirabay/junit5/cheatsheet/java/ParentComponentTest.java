package hirabay.junit5.cheatsheet.java;

import hirabay.junit5.cheetsheet.java.ChildComponent;
import hirabay.junit5.cheetsheet.java.ParentComponent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// @InjectMocksや@Mockを動作させるために必要
@ExtendWith(MockitoExtension.class)
class ParentComponentTest {
    // テスト対象のクラス。「@InjectMocks」を付けると自動で依存クラスを注入してくれる
    @InjectMocks
    ParentComponent parentComponent;

    // テスト対象クラスの依存クラス。「@Mock」を付けるとMockオブジェクトを生成してくれる
    @Mock
    ChildComponent childComponent;

    @BeforeEach
    void beforeEach() {
        System.out.println("各テストケース実行前の処理");
    }

    @AfterEach
    void afterEach() {
        System.out.println("各テストケース実行後の処理");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("テスト実行前の処理");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("テスト実行後の処理");
    }

    @Nested
    class GreetingTest {
        @Test
        void test() {
            // 依存Mockの動作を定義
            when(childComponent.greeting(any())).thenReturn("Hello");

            var actual = parentComponent.greeting("hirabay");
            var expected = "Hello";

            // 戻り値を検証
            assertThat(actual).isEqualTo(expected);
            // 依存オブジェクトの呼び出され方を検証
            verify(childComponent).greeting(any());
            // 以下コードと同じ意味。timesで呼び出し回数を指定できます。
            // verify(childComponent, times(1)).greeting(any());
        }
    }


}