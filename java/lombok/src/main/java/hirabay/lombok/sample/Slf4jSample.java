package hirabay.lombok.sample;

import lombok.extern.slf4j.Slf4j;

// ロガーを利用したいクラスに付与
@Slf4j
public class Slf4jSample {
    public void sample() {
        var message = "hello, world.";
        log.info("message: {}", message);
    }
}
