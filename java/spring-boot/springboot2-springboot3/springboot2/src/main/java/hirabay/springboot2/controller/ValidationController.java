package hirabay.springboot2.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ValidationController {

    @PostMapping("/validate")
    public Object sample(
            @Valid
            @RequestBody
            SampleRequestBody body
    ) {
//        return Map.of(
//                "datetime", datetime
//        );
        return body;
    }

    @Data
    public static class SampleRequestBody {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private ZonedDateTime datetime;
    }
}
