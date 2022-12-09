package rhirabay.gcp.linebot.domain.model.tdl;

import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @param id LineID
 * @param item タスク
 */
public record TdlResource(String id, String item) {

    public static final String CHECKED_EMOJI = "✅";

    public boolean isComplete() {
        return item.startsWith(CHECKED_EMOJI);
    }

    @SneakyThrows
    public static List<TdlResource> of(CallbackRequest request) {

        return request.getEvents().stream()
                .map(event -> {
                    if (event instanceof MessageEvent<?> messageEvent) {
                        if (messageEvent.getMessage() instanceof TextMessageContent content) {
                            return new TdlResource(
                                    event.getSource().getSenderId(),
                                    content.getText()
                            );
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public static List<TdlResource> of(TableResult tableResult) {
        var tdlResources = new ArrayList<TdlResource>();
        tableResult.iterateAll().forEach(result -> {
            tdlResources.add(TdlResource.of(result));
        });
        return tdlResources;
    }

    public static TdlResource of(FieldValueList fieldValues) {
        var channelId = fieldValues.get("channel_id").getStringValue();
        var item = fieldValues.get("item_name").getStringValue();
        return new TdlResource(
                channelId,
                item
        );
    }
}
