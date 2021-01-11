package toma.meteo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"toma.meteo"})
@EnableScheduling
@SpringBootApplication
public class MeteoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeteoApplication.class, args);
	}

}
