package thkoeln.st.st2praktikum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import thkoeln.st.st2praktikum.exercise.Exercise0;


@SpringBootApplication
public class ProjectApplication {

	/**
	 * Entry method
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		//SpringApplication.run(ProjectApplication.class, args);
		Exercise0 a = new Exercise0();
		System.out.println(a.go("[no,1]"));
		System.out.println(a.go("[ea,5]"));
		System.out.println(a.go("[so,5]"));
		System.out.println(a.go("[we,2]"));
	}
}
