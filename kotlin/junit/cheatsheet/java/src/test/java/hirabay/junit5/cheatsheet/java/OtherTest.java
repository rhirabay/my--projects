package hirabay.junit5.cheatsheet.java;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OtherTest {
    @Test
    void staticMethod() {
        var mockedValue = LocalDate.of(2022, 3, 1);

        // Mock化の準備。close()の必要があるので try with resource文で
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            // staticメソッドのMock化
            mockedLocalDate.when(() -> LocalDate.now()).thenReturn(mockedValue);

            // staticメソッドの呼び出し
            var actual = LocalDate.now();

            // 戻り値の検証
            assertThat(actual.toString()).isEqualTo("2022-03-01");

            // staticメソッドの呼び出され方の検証
            mockedLocalDate.verify(() -> LocalDate.now());
        }
    }

    @Test
    void constructor() {
        // Mock化の準備。close()の必要があるので try with resource文で
        try (MockedConstruction<Random> mockedRandom = mockConstruction(Random.class)) {
            // Mock化されたインスタンスが生成される
            var random = new Random();

            // Mock動作を指定
            // 注意：コンストラクタの呼び出し後に実装すること！
            mockedRandom.constructed().forEach(constructed -> {
                when(constructed.nextInt()).thenReturn(1);
            });

            assertThat(random.nextInt()).isEqualTo(1);
            assertThat(random.nextInt()).isEqualTo(1);
            assertThat(random.nextInt()).isEqualTo(1);

            // Mock呼び出され方を検証
            mockedRandom.constructed().forEach(constructed -> {
                verify(constructed, times(3)).nextInt();
            });
        }
    }
}
