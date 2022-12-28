package rhirabay.gcp.linebot.util.garbage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GarbageCalender {
    /**
     * 市町村ごとの翌日のゴミ収集対象を返却する
     * @param city 対象の市
     * @return 翌日のゴミ収集対象
     */
    public static List<String> nextDay(String city, ZonedDateTime now) {
        var nextDay = now.plusDays(1);
        return today(city, nextDay);
    }

    public static List<String> today(String city, ZonedDateTime now) {
        return switch (city) {
            case "hino":
                yield hino(now);
            default:
                yield Collections.emptyList();
        };
    }

    private static List<String> hino(ZonedDateTime now) {
        var diffWeeks = ChronoUnit.WEEKS.between(BASE_DAY, now);

        return switch (now.getDayOfWeek()) {
            case MONDAY:
                if (diffWeeks % 2 == 1) {
                    yield Arrays.asList("可燃ゴミ", "段ボール");
                } else {
                    yield Arrays.asList("可燃ゴミ");
                }
            case TUESDAY:
                if (diffWeeks % 2 == 1) {
                    yield Arrays.asList("雑誌・雑紙");
                } else {
                    yield Collections.emptyList();
                }
            case WEDNESDAY:
                if (diffWeeks % 4 == 0) {
                    yield Arrays.asList("不燃ゴミ");
                } else if (diffWeeks % 4 == 1) {
                    yield Arrays.asList("小型家電・金属");
                } else if (diffWeeks % 4 == 2) {
                    yield Arrays.asList("ペットボトル");
                } else {
                    yield Arrays.asList("新聞");
                }
            case THURSDAY:
                if (diffWeeks % 2 == 1) {
                    yield Arrays.asList("可燃ゴミ", "古着・古布");
                } else {
                    yield Arrays.asList("可燃ゴミ");
                }
            case FRIDAY:
                if (diffWeeks % 2 == 1) {
                    yield Arrays.asList("プラスチック", "有害ごみ", "かん");
                } else {
                    yield Arrays.asList("プラスチック", "有害ごみ", "びん");
                }
            default:
                yield Collections.emptyList();
        };
    }

    public static final ZonedDateTime BASE_DAY = ZonedDateTime.parse("2023-01-02T00:00:00+09:00");
}
