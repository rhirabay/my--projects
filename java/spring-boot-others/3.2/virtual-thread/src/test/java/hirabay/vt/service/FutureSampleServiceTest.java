package hirabay.vt.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class FutureSampleServiceTest {
    @Autowired
    private SampleService sampleService;

    @Test
    void test_sample() throws Exception {
        var start = System.currentTimeMillis();

        var result1 = sampleService.sample(1);
        System.out.println(result1);

        var result2 = sampleService.sample(2);
        System.out.println(result2);

        var end = System.currentTimeMillis();

        System.out.printf("time: %s ms%n", end - start);
    }

    @Autowired
    FutureSampleService futureSampleService;

    @Test
    void test_future_sample() throws Exception {
        var start = System.currentTimeMillis();

        // 並列で実行したい処理をすべて呼び出す
        var resultFuture1 = futureSampleService.sample(1);
        var resultFuture2 = futureSampleService.sample(2);
        // 結果をjoinで取得する
        var result1 = resultFuture1.join();
        var result2 = resultFuture2.join();
        // 結果を利用する（以降は直列処理と一緒）
        System.out.println(result1);
        System.out.println(result2);

        var end = System.currentTimeMillis();
        System.out.printf("time: %s ms%n", end - start);
    }

    @Test
    void test_future_sample_ng() throws Exception {
        var start = System.currentTimeMillis();

        var resultFuture1 = futureSampleService.sample(1);
        var result1 = resultFuture1.join(); // joinを呼び出すと、処理が終わるまでブロックする
        System.out.println(result1);

        var resultFuture2 = futureSampleService.sample(2);
        var result2 = resultFuture2.join();
        System.out.println(result2);

        var end = System.currentTimeMillis();
        System.out.printf("time: %s ms%n", end - start);
    }

    @Test
    void test_future_sample_exception() {

        var resultFuture1 = futureSampleService.error(1);
        var resultFuture2 = futureSampleService.error(2);

        // try - catchで例外処理
        try {
            resultFuture1.join();
        } catch (RuntimeException ex) { // 例外はすべてCompletionExceptionにwrapされる
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getMessage());
        }

        // メソッドチェーンで例外処理
        var result = resultFuture2.exceptionally(ex -> {
            System.out.println(ex.getClass().getName());
            // 例外はすべてCompletionExceptionにwrapされる
            if (ex instanceof RuntimeException runtimeException) {
                return runtimeException.getMessage();
            } else {
                return "unexpected";
            }
        }).join();
        System.out.println(result);
    }

    @Test
    void test() throws InterruptedException {
        var actual1 = futureSampleService.sample(1);
        assertThat(actual1.join()).isEqualTo("result 1");

        var actual2 = futureSampleService.error(2);
        var completionException = assertThrows(CompletionException.class, actual2::join);
        assertThat(completionException.getCause().getMessage()).isEqualTo("error 2");
    }
}