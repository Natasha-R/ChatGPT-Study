package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


import java.util.UUID;

@EnableJpaRepositories
public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    //List<TidyUpRobot> findByRoom(Walkable room);
}
