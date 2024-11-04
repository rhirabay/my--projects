package hirabay.migration.mockmvctester;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class SampleControllerTest {
    @Nested
    @WebMvcTest(SampleController.class)
    class Before {
        @Autowired
        private MockMvc mockMvc;

        @Test
        void test() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/mockMvcTester/sample"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                    {
                      "message": "Hello, World!",
                      "detail": {
                        "key1": "value1",
                        "key2": "value2"
                      }
                    }
                    """));
        }
    }

    @Nested
    class After {
        private final MockMvcTester mockMvc = MockMvcTester.of(new SampleController());

        @Test
        void test() {

            mockMvc.get()
                    .uri("/mockMvcTester/sample")
                    .assertThat()
                    .hasStatusOk()
                    .bodyJson().isLenientlyEqualTo("""
                    {
                      "message": "Hello, World!",
                      "detail": {
                        "key1": "value1",
                        "key2": "value2"
                      }
                    }
                    """);
        }
    }
}