package hirabay.migration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
//@Validated // check Bean Validation of Configuration Properties
@Component
@ConfigurationProperties(prefix = "sample")
public class SampleProperties {
    @Pattern(regexp = "main")
    private String value;

    private SubProperties sub;

    @Data
    public static class SubProperties {
        @Pattern(regexp = "sub")
        private String value;
    }
}
