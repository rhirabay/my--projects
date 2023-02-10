package hirabay.lombok.sample;

import lombok.extern.slf4j.Slf4j;

// ロガーを利用したいクラスに付与
@Slf4j
public class Slf4jSample {
    public void sample() {
        var message = "hello, world.";
        log.info("message: {}", message);
        // 11:17:31.555 [Test worker] INFO hirabay.lombok.sample.Slf4jSample - message: hello, world.

        log.info("{}, {}.", "Hello", "world"); // Hello, world.
    }
}
