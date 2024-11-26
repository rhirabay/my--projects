package hirabay.migration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleService {
    private final SampleClient sampleClient;

    public void sample() {
        var greeting = sampleClient.hello();
        System.out.println(greeting);
    }
}
