package hirabay.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class VirtualThread {
    @Test
    void normal_thread() {
        new Thread(() -> {
            log.info("Hello world.");
            log.info("isVirtual: {}", Thread.currentThread().isVirtual());
        }).start();
    }

    @Test
    void virtual_thread() {
        Thread.startVirtualThread(() -> {
            log.info("isVirtual: {}", Thread.currentThread().isVirtual());
        });
    }
}
