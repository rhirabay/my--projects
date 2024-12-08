package hirabay.springdoc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Validated
@Tag(name = "Sample API")
public class SampleController {

    @PostMapping(
            value = "/sample",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "ユーザ登録", description = "リクエストで指定された情報でユーザを登録します。")
    public void register(@RequestBody @Valid User request) {
        return;
    }

    @GetMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "ユーザ情報取得", description = "リクエストで指定されたidでユーザを検索し、情報を返却します。")
    public Mono<User> get(
            @Pattern(regexp = "[0-9a-f-]{36}") @Parameter(example = "123", description = "ユーザ識別子")
                    String id) {
        return Mono.just(new User("1234567890", "hogehoge", 30));
    }

    @Value
    public static class User {
        @NotNull
        @Pattern(regexp = "[0-9a-f-]{36}")
        @Schema(title = "ユーザ識別子", description = "一意になるようUUID等を設定してください。", example = "1")
        private String id;

        @NotNull private String name;

        @NotNull
        @Min(1L)
        @Max(100L)
        private int age;
    }
}
