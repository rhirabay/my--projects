package rhirabay.gcp.linebot.util.garbage;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class GarbageCalenderTest {
    @Nested
    public class NextDayTest {
        @ParameterizedTest
        @CsvSource(delimiter = '|', value = {
                "2023-01-04 | 不燃ゴミ",
                "2023-01-05 | 可燃ゴミ",
                "2023-01-06 | プラスチック/有害ごみ/びん",
                "2023-01-09 | 可燃ゴミ/段ボール",
                "2023-01-10 | 雑誌・雑紙",
                "2023-01-11 | 小型家電・金属",
                "2023-01-12 | 可燃ゴミ/古着・古布",
                "2023-01-13 | プラスチック/有害ごみ/かん",
                "2023-01-18 | ペットボトル",
                "2023-01-25 | 新聞",
        })
        void testHino(String date, String expected) {
            var datetimeFormat = "%sT00:00:00+09:00";
            var now = ZonedDateTime.parse(datetimeFormat.formatted(date));
            var actual = GarbageCalender.today("hino", now);

            assertThat(actual.toArray()).isEqualTo(expected.split("/"));
        }

        @Test
        void testHinoEmpty() {
            var now = ZonedDateTime.parse("2023-01-16T00:00:00+09:00");
            var actual = GarbageCalender.nextDay("hino", now);
            assertThat(actual).isEmpty();
        }
    }
}