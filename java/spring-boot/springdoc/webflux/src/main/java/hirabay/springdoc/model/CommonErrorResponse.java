package hirabay.springdoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Value;

@Data
public class CommonErrorResponse {
    @Schema(title = "エラーコード", example = "0001")
    private String errorCode;
    @Schema(title = "エラーメッセージ", example = "予期せぬエラーが発生しました。")
    private String errorMessage;
}
