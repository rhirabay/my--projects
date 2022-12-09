package rhirabay.gcp.linebot.controller;

import com.linecorp.bot.model.event.CallbackRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rhirabay.gcp.linebot.domain.model.tdl.TdlResource;
import rhirabay.gcp.linebot.usecase.TdlUsecase;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ToDoListController {
    private final TdlUsecase userCase;

    @PostMapping("/v1/tdl")
    public String webhook(@RequestBody CallbackRequest request) {
        log.info("request: {}", request);
        var resources = TdlResource.of(request);

        resources.forEach(userCase::handleEvent);

        return "ok";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public String handleException(Exception ex) {
        log.error("failed to handle webhook.", ex);
        return "ng";
    }

}
