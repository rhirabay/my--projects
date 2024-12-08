package hirabay.device;

import static org.springframework.http.HttpHeaders.USER_AGENT;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua_parser.Client;
import ua_parser.Parser;

@RestController
@Slf4j
public class SampleController {
    @GetMapping("/**")
    public Object sample(HttpServletRequest request) {
        var uaValue = request.getHeader(USER_AGENT);

        Parser uaParser = new Parser();
        Client client = uaParser.parse(uaValue);

        return client;
    }
}
