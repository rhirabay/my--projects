package hirabay.runn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@SpringBootApplication
public class RunnApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunnApplication.class, args);
	}

	@GetMapping("/sample")
	public Object sample(@RequestParam(required = false, defaultValue = "anonymous") String name) {
		return Map.of(
				"name", name
		);
	}

	@PostMapping("/sample")
	public Object samplePost(@RequestBody Map<String, String> body) {
		return body;
	}

}
