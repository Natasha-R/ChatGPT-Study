package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {

}
