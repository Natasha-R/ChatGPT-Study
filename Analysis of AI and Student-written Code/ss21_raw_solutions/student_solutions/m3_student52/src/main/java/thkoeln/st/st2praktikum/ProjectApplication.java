package thkoeln.st.st2praktikum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("thkoeln.st.st2praktikum.*")
@EntityScan("thkoeln.st.st2praktikum.*")
public class ProjectApplication {

	/**
	 * Entry method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}
