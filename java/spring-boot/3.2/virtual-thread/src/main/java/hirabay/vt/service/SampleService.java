package hirabay.vt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class SampleService {
    public String sample(int num) throws InterruptedException {
        System.out.println("start %s.".formatted(num));
        TimeUnit.SECONDS.sleep(5);
        System.out.println("end %s.".formatted(num));
        return "result %s".formatted(num);
    }
}
