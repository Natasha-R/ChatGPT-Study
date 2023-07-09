package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface RobotRepository extends CrudRepository<TidyUpRobot, UUID>
{
}
