package rhirabay.gcp.linebot.infra;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("line-bot")
public class LineMessagingClientProperties {
    private Map<String, ChannelProperties> channels;

    public String getToken(String channel) {
        return this.channels.get(channel).getToken();
    }

    @Data
    public static class ChannelProperties {
        private String token;
    }
}