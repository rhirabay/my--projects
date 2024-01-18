package hirabay.vt.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class FutureSampleService {
    @Async // @Asyncアノテーションを付与する（privateのような内部で呼び出すメソッドに付与しても意味ないので注意）
    public CompletableFuture<String> sample(int num) throws InterruptedException {
        System.out.println("start %s.".formatted(num));
        //TimeUnit.SECONDS.sleep(5);
        System.out.println("end %s.".formatted(num));

        // 戻り値は、「CompletableFuture.completedFuture」でwrapする
        return CompletableFuture.completedFuture("result %s".formatted(num));
    }

    @Async
    public CompletableFuture<String> error(int num) {
        throw new RuntimeException("error %s".formatted(num));
    }
}
