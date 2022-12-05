package rhirabay.lib;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

//@Component
public class SampleComponent {
    public SampleComponent() {
        System.out.println("SampleComponent is registered.");
    }

//    @PostConstruct
//    void setup() {
//        System.out.println("SampleComponent is registered.");
//    }
}
