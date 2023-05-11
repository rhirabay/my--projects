package hirabay.springboot.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SampleClient {

    @Retryable(
//            backoff = @Backoff(delayExpression = "#{${hirabay.retry.backoff}}"),
            backoff = @Backoff(delayExpression = "${hirabay.retry.backoff}"),
            maxAttemptsExpression = "${hirabay.retry.attempt}"
    )
    public String hello() {
        log.info("hello.");
        throw new RuntimeException("test");
    }
}
