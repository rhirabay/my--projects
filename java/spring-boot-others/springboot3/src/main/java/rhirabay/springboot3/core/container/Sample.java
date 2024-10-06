package rhirabay.springboot3.core.container;

import jakarta.annotation.PostConstruct;
//import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Sample {
    @PostConstruct
    void init() {
        // something to do
    }
}
