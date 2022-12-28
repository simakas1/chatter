package lt.vaskevicius.chatter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class ChatterApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatterApplication.class, args);
	}
}