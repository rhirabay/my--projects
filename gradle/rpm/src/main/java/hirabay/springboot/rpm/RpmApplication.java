package hirabay.springboot.rpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RpmApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpmApplication.class, args);
	}

	@GetMapping("/**")
	public String hello() {
		return "Hello world.";
	}
}
