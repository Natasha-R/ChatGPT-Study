package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.UUID;

public interface TidyUpRepository extends CrudRepository<TidyUpRobot, UUID> {


    void deleteById(UUID cleaningDevice);
    TidyUpRobot findTidyUpRobotById(UUID tidyUpRobot);
    TidyUpRobot getTidyUpRobotById(UUID tidyUpRobot);
    TidyUpRobot findTidyUpRobotByName(String name);
}
