package rhirabay.gcp.linebot.controller;

import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rhirabay.gcp.linebot.domain.model.BreastFeedingResource;
import rhirabay.gcp.linebot.domain.value.LineChannelId;
import rhirabay.gcp.linebot.usecase.BreastFeedingUsecase;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RyobotController {
    private final BreastFeedingUsecase breastFeedingUsecase;

    @PostMapping("/v1/childCare")
    public String chileCare(CallbackRequest request) {
        request.getEvents().forEach(event -> {
            var channelId = LineChannelId.of(event);
            if (event instanceof MessageEvent<?> messageEvent) {
                if (messageEvent.getMessage() instanceof TextMessageContent content) {
                    handleTextMessageEvent(content, channelId);
                }
            }
        });
        return "ok";
    }

    private void handleTextMessageEvent(TextMessageContent content, LineChannelId channelId) {
        // 授乳記録
        if (content.getText().startsWith(BreastFeedingResource.PREFIX)) {
            var resource = new BreastFeedingResource(content.getText(), channelId);
            breastFeedingUsecase.execute(resource);
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex) {
        log.error("failed to handle webhook.", ex);
        return "ng";
    }
}
