package rhirabay.gcp.linebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rhirabay.gcp.linebot.service.BreastFeedingRecordService;
import rhirabay.gcp.linebot.service.TdlService;
import rhirabay.gcp.linebot.usecase.TdlUsecase;

import java.time.ZonedDateTime;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final BreastFeedingRecordService target;

}
