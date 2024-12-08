package rhirabay.grpc;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.Map;

@Data
@Validated
@ConfigurationProperties("rhirabay.grpc")
public class GrpcProperties {
    @Valid
    private Map<String, GrpcClientProperties> client;

    @Data
    public static class GrpcClientProperties {
        @Nonnull
        private String host;
        private int port;
    }
}
