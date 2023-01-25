package hirabay.junit5.cheetsheet.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheatsheetApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CheatsheetApplication.class);
        app.run(args);
    }
}
