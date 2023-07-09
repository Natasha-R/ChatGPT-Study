package thkoeln.st.st2praktikum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class ProjectApplication {

	/**
	 * Entry method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}
