package rhirabay.gcp.linebot.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.gcp.linebot.service.GarbageNoticeService;
import rhirabay.gcp.linebot.util.garbage.GarbageCalender;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class GarbageNoticeUsecase {
    private final GarbageNoticeService garbageNoticeService;

    public void execute() {
        var now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        var garbage = GarbageCalender.nextDay("hino", now).stream()
                .map("「%s」"::formatted)
                .toList();

        // 出せるゴミがなければ終了
        if (garbage.isEmpty()) return;

        var message = """
                【ゴミ出しのお知らせ】
                明日は%sの日だよ！
                """.formatted(String.join("と", garbage));

        garbageNoticeService.notice(message);
    }
}
