package hirabay.migration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "sample")
public class SampleProperties {
    @Pattern(regexp = "main")
    private String value;
//    @Valid
    private SubProperties sub;

    @Data
    @Valid
    public static class SubProperties {
        @Pattern(regexp = "sub")
        private String value;
    }
}
