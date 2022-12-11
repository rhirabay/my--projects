package rhirabay.gcp.linebot.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.gcp.linebot.domain.value.LineChannelId;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static rhirabay.gcp.linebot.domain.model.BreastFeedingResource.*;

/**
 * 授乳記録をするためのServiceクラス
 */
@Component
@RequiredArgsConstructor
public class BreastFeedingRecordService {
    private final LineMessagingClient ryobotLineMessagingClient;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("HH時mm分");

    public ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
    }

    public void sendMessage(ZonedDateTime recordedTime, boolean isMilk, LineChannelId channelId) {
        var title = "前回: %s (%s)".formatted(
                recordedTime.format(DATE_FORMAT),
                isMilk ? MILK : BREAST_FEEDING
        );
        var subTitle = "次の目安: %s".formatted(recordedTime.plusHours(3).format(DATE_FORMAT));
        var buttons = Arrays.asList(
                Button.builder()
                        .action(new MessageAction(BREAST_FEEDING, PREFIX + BREAST_FEEDING))
                        .build(),
                Button.builder()
                        .action(new MessageAction(MILK, PREFIX + MILK))
                        .build()
        ).stream().map(i -> (FlexComponent)i).toList();
        var bubbles = Arrays.asList(
                Bubble.builder()
                        .header(Box.builder()
                                .contents(
                                        Text.builder()
                                                .text(title)
                                                .build(),
                                        Text.builder()
                                                .text(subTitle)
                                                .build()
                                )
                                .layout(FlexLayout.VERTICAL)
                                .build())
                        .body(Box.builder()
                                .contents(buttons)
                                .layout(FlexLayout.VERTICAL)
                                .build())
                        .build()
        );
        var flexMessage = FlexMessage.builder()
                .altText("いつもありがとう ^-^!")
                .contents(Carousel.builder()
                        .contents(bubbles)
                        .build())
                .build();

        ryobotLineMessagingClient.pushMessage(new PushMessage(channelId.value(), flexMessage));
    }
}
