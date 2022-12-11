package rhirabay.gcp.linebot.domain.value;

import com.linecorp.bot.model.event.Event;

public record LineChannelId(String value) {
    public static LineChannelId of(Event event) {
        return new LineChannelId(event.getSource().getSenderId());
    }
}
