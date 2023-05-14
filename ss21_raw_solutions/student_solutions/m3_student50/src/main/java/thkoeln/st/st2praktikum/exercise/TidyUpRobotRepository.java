package thkoeln.st.st2praktikum.exercise;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.Room;

import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot,UUID>{

    TidyUpRobot findTidyUpRobotById(UUID tidyUpRobot);
    TidyUpRobot getTidyUpRobotById(UUID tidyUpRobot);
    TidyUpRobot findTidyUpRobotByName(String name);
}
