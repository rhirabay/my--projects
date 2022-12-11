package rhirabay.gcp.linebot.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.gcp.linebot.domain.model.BreastFeedingResource;
import rhirabay.gcp.linebot.service.BreastFeedingRecordService;

@Component
@RequiredArgsConstructor
public class BreastFeedingUsecase {
    private final BreastFeedingRecordService service;

    public void execute(BreastFeedingResource resource) {
        service.sendMessage(service.now(), resource.isMilk(), resource.channelId());
    }
}
