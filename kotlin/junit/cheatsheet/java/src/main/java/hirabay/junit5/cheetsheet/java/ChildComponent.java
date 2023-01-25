package hirabay.junit5.cheetsheet.java;

import org.springframework.stereotype.Component;

@Component
public class ChildComponent {
    public String greeting(String name) {
        return "Hello, " + name + ".";
    }
}
