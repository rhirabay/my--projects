package rhirabay.gcp.linebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rhirabay.gcp.linebot.usecase.GarbageNoticeUsecase;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GarbageNoticeController {
    private final GarbageNoticeUsecase garbageNoticeUsecase;

    @GetMapping("/v1/garbage/tomorrow")
    public void tomorrowGarbage() {
        garbageNoticeUsecase.execute();
    }
}
