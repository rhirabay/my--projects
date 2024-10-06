package hirabay.springboot.restclient;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/restClient/proxyFactory")
public interface HttpServiceProxyFactoryClient {
    @GetExchange(url = "/greeting")
    String greeting(@RequestParam("name") String name);
}
