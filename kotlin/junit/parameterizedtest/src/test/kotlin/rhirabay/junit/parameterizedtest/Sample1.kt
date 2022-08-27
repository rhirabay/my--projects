package rhirabay.junit.parameterizedtest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

data class Score(val value: Int) {
    /**
     * 試験の合否結果を返却する
     * 80点以上：true(合格)
     * 80点未満：false(不合格)
     */
    fun ok(): Boolean {
        return value >= 80
    }

    fun result(): Result {
        return if (value >= 80) {
            Result.OK
        } else {
            Result.NG
        }
    }
}

enum class Result {
    OK,
    NG
}

class ValueSourceSample1 {
    @Nested
    inner class Before {
        @Test
        @DisplayName("79点の場合、不合格")
        fun score_79() {
            val score = Score(79)
            assertFalse(score.ok())
        }

        @Test
        @DisplayName("80点の場合、合格")
        fun score_80() {
            val score = Score(80)
            assertFalse(score.ok())
        }
    }

    @Nested
    inner class AfterValueSource {
        @ParameterizedTest
        @ValueSource(ints = [
            50, // 代表値
            79  // 境界値
        ])
        @DisplayName("80点未満の場合、不合格")
        fun no_ok(scoreValue: Int) {
            val score = Score(scoreValue)
            assertFalse(score.ok())
        }

        @ParameterizedTest
        @ValueSource(ints = [
            90, // 代表値
            80  // 境界値
        ])
        @DisplayName("80点以上の場合、合格")
        fun ok(scoreValue: Int) {
            val score = Score(scoreValue)
            assertFalse(score.ok())
        }
    }

    @Nested
    inner class AfterCsvSource {
        @ParameterizedTest
        @CsvSource(delimiter = '|', value = [
            // 点数 | 合否
            " 50 | false", // 不合格（代表値）
            " 79 | false", // 不合格（境界値）
            " 80 | true",  // 合格（境界値）
            " 90 | true",  // 合格（代表値）
        ])
        fun test(scoreValue: Int, expected: Boolean) {
            val score = Score(scoreValue)
            assertThat(score.ok()).isEqualTo(expected)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AfterMethodSource {
        @ParameterizedTest
        @MethodSource("source")
        fun test(scoreValue: Int, expected: Boolean) {
            val score = Score(scoreValue)
            assertThat(score.ok()).isEqualTo(expected)
        }

        private fun source() = listOf(
            arguments(50, false),
            arguments(79, false),
            arguments(80, true),
            arguments(90, true),
        )
    }
}