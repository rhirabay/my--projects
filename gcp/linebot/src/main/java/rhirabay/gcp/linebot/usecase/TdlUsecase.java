package rhirabay.gcp.linebot.usecase;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.FlexComponent;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.gcp.linebot.domain.model.tdl.TdlResource;
import rhirabay.gcp.linebot.service.TdlService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TdlUsecase {
    private final LineMessagingClient tdlLineMessagingClient;
    private final TdlService service;

    public void handleEvent(TdlResource resource) {
        if (resource.isComplete()) {
            // タスクを削除する
            service.delete(resource);
        } else {
            // メッセージで受け取ったタスクを保存する
            service.save(resource);
        }
        // channelに紐づくTDLを全件取得する
        var items = service.findAllItemsByChannel(resource.id())
                .stream()
                .map(TdlResource::item)
                .toList();
        // TDLをメッセージ送信する
        this.sendMessageById(resource.id(), items);
    }

    public void sendMessageById(String id, List<String> items) {
        if (items.isEmpty()) {
            this.sendCompletedMessage(id);
            return;
        }

        var buttons = items.stream()
                .map(item -> Button.builder()
                        .action(new MessageAction(item, TdlResource.CHECKED_EMOJI + item))
                        .margin(FlexMarginSize.XS)
                        .height(Button.ButtonHeight.SMALL)
                        .color("#aaeeaa")
                        .style(Button.ButtonStyle.SECONDARY)
                        .build())
                .map(button -> (FlexComponent)button)
                .toList();
        var box = Box.builder()
                .contents(buttons)
                .layout(FlexLayout.VERTICAL)
                .build();
        var flexMessage = FlexMessage.builder()
                .contents(Bubble.builder()
                        .body(box)
                        .build())
                .altText("%d件あるよ".formatted(items.size()))
                .build();

        tdlLineMessagingClient.pushMessage(new PushMessage(id, flexMessage));
    }

    public void sendCompletedMessage(String id) {
        TextMessage textMessage = TextMessage.builder()
                .text("全部終わったよ！\nやることを追加してね！")
                .build();
        tdlLineMessagingClient.pushMessage(new PushMessage(id, textMessage));
    }
}
