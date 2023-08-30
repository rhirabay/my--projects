package hirabay.springdoc;

import hirabay.springdoc.model.CommonErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
public class SampleControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonErrorResponse handleException() {
        return new CommonErrorResponse();
    }
}
