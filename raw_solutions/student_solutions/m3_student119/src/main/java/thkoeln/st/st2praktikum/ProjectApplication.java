package thkoeln.st.st2praktikum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication

public class ProjectApplication {
    private static final Logger log = LoggerFactory.getLogger(ProjectApplication.class);

    /**
     * Entry method
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
