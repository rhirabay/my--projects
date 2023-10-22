package hirabay.springboot.virtualthread;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/virtual-thread")
public class VirtualThreadController {
    /**
     * curlコマンド
     * curl -X GET http://localhost:8081/virtual-thread/is-virtual
     */
    @GetMapping("/is-virtual")
    public Object isVirtual() {
        return Thread.currentThread().isVirtual();
    }

    /**
     * 負荷試験用。1秒間スリープする。
     */
    @GetMapping("/sleep")
    public Object sleep() throws InterruptedException {
        Thread.sleep(1000);
        return "OK";
    }
}
