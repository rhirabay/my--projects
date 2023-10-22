package hirabay.springboot.apachepulsar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
