package rhirabay.gcp.linebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rhirabay.gcp.linebot.service.TdlService;
import rhirabay.gcp.linebot.usecase.TdlUsecase;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TdlService tdlService;
    private final TdlUsecase userCase;

//    @GetMapping("/test")
    public Object test() {
        var items = Arrays.asList("a", "b");
        userCase.sendMessageById("U4e19ea3aed98d90d540a6448ee11c75b", items);
        return null;
    }
}
