package thkoeln.st.st2praktikum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroidService;

import java.util.UUID;


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
