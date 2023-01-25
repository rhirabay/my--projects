package hirabay.junit5.cheatsheet.java;

import hirabay.junit5.cheetsheet.java.CheatsheetApplication;
import hirabay.junit5.cheetsheet.java.ChildComponent;
import hirabay.junit5.cheetsheet.java.ParentComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CheatsheetApplication.class)
public class SpringBootTestSample {
    @MockBean
    private ChildComponent childComponent;

    @Autowired
    private ParentComponent parentComponent;

    @Test
    void test() {
        when(childComponent.greeting(any())).thenReturn("dummy");

        var actual = parentComponent.greeting("hirabay");
        var expected = "dummy";

        assertThat(actual).isEqualTo(expected);
    }
}
