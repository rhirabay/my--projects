package rhirabay.grpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("rhirabay.grpc")
public class GrpcProperties {
    private Map<String, GrpcClientProperties> client;

    @Data
    public static class GrpcClientProperties {
        private String host;
        private int port;
    }
}
