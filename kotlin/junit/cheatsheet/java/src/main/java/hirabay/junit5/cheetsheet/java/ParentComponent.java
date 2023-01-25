package hirabay.junit5.cheetsheet.java;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ParentComponent {
    private final ChildComponent childComponent;

    public String greeting(String name) {
        return childComponent.greeting(name);
    }
}
