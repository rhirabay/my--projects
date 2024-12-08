package hirabay.webmvc.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlSample {
    @GetMapping(value = "/xml1", produces = "application/xml")
    public Object xml1() {
        return XmlSampleModel1.builder().key1("value1").key2("value2").build();
    }

    @GetMapping(value = "/xml2", produces = "application/xml")
    public Object xml2() {
        return XmlSampleModel2.builder().key3("value3").key4("value4").build();
    }

    @GetMapping(value = "/xmlClient1")
    public Object xmlClient1() {
        var restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080").build();

        return restTemplate.getForObject("/xml1", XmlSampleModel3.class);
    }

    @GetMapping(value = "/xmlClient2")
    public Object xmlClient2() {
        var restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080").build();

        return restTemplate.getForObject("/xml2", XmlSampleModel3.class);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JacksonXmlRootElement(localName = "root1")
    public static class XmlSampleModel1 {
        @JacksonXmlProperty(localName = "key1")
        private String key1;

        @JacksonXmlProperty(localName = "key2")
        private String key2;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JacksonXmlRootElement(localName = "root2")
    public static class XmlSampleModel2 {
        @JacksonXmlProperty(localName = "key3")
        private String key3;

        @JacksonXmlProperty(localName = "key4")
        private String key4;
    }

    @Data
    public static class XmlSampleModel3 {
        @JacksonXmlProperty(localName = "key1")
        private String key1;

        @JacksonXmlProperty(localName = "key2")
        private String key2;

        @JacksonXmlProperty(localName = "key3")
        private String key3;

        @JacksonXmlProperty(localName = "key4")
        private String key4;
    }
}
