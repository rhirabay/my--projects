package hirabay.thymeleaf.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class FormSampleController {
    @GetMapping("/form")
    public String get(Model model) {
        var form = new SampleForm("hirabay", List.of("programming", "reading"));

        model.addAttribute("form", form);

        return "form";
    }

    @PostMapping("/form")
    public String post(@ModelAttribute SampleForm form, Model model) {
        log.info("form: {}", form);
        model.addAttribute("form", form);

        return "form";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SampleForm {
        private String name;
        private List<String> hobbies;
    }
}
