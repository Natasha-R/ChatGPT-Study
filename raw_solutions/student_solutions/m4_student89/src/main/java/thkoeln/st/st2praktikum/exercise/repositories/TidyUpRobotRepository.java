package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


import java.util.UUID;

@Repository
public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot,UUID> {
    public TidyUpRobot getTidyUpRobotByid(UUID id);
    public void removeTidyUpRobotByid(UUID id);
}
