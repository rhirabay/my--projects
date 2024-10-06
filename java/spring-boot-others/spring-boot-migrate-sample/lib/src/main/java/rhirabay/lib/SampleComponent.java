package rhirabay.lib;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SampleComponent {
    @PostConstruct
    void setup() {
        System.out.println("SampleComponent is registered.");
    }
}
