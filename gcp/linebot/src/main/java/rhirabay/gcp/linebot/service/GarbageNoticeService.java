package rhirabay.gcp.linebot.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GarbageNoticeService {
    private final LineMessagingClient ryobotLineMessagingClient;

    private static final List<String> NOTICE_TARGET_LIST = Arrays.asList(
            "U4e19ea3aed98d90d540a6448ee11c75b",
            "U4c6d03a8943e9a4ca2de37de5e9fee32"
    );

    public void notice(String message) {
        var textMessage = TextMessage.builder()
                .text(message)
                .build();
        NOTICE_TARGET_LIST.forEach(i -> {
            ryobotLineMessagingClient.pushMessage(new PushMessage(i, textMessage));
        });
    }
}
