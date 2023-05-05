package thkoeln.st.st2praktikum;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ProjectApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	/**
	 * Entry method
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}
