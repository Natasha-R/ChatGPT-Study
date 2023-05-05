package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.Transportable;

import java.util.UUID;

@EnableJpaRepositories
public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID>
{

}
