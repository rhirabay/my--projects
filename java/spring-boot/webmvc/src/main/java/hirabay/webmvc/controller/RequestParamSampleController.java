package hirabay.webmvc.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import hirabay.webmvc.controller.resolver.ParamName;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestParamSampleController {
    @GetMapping("/request-param")
    public Object test(SampleModel model) {
        return model;
    }

    @Data
    public static class SampleModel {
        @JsonProperty("a")
        @ParamName("a")
        private String aParam;
        private String b;
    }
}
