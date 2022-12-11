package rhirabay.gcp.linebot.domain.model;

import rhirabay.gcp.linebot.domain.value.LineChannelId;

public record BreastFeedingResource(String message, LineChannelId channelId) {
    public static final String PREFIX = "🍼";
    public static final String MILK = "ミルク";
    public static final String BREAST_FEEDING = "授乳";

    public boolean isMilk() {
        return message.contains(MILK);
    }
}
