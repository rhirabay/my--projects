package hirabay.webmvc.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonAndXmlSupportedRestController {
    // curl localhost:8080/jsonXml -H "Accept: application/json"
    // curl localhost:8080/jsonXml -H "Accept: application/xml"
    @GetMapping(
            value = "/jsonXml",
            produces = {"application/xml", "application/json"})
    public SampleModel jxonXml() {
        var sampleModel = new SampleModel();
        sampleModel.setId("1");
        sampleModel.setName("sample");
        return sampleModel;
    }

    @Data
    public static class SampleModel {
        private String id;
        private String name;
    }
}
