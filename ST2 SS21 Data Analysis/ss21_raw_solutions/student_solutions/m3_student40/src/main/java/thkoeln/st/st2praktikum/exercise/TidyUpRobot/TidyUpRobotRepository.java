package thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, TidyUpRobot> {
    public List<TidyUpRobot> findById( UUID id );
}
