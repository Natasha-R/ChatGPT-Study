package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import java.util.UUID;

@Repository
public interface TidyUpRobotRepo extends JpaRepository<TidyUpRobot,Integer> {
    TidyUpRobot findTidyUpRobotById(UUID id);
    TidyUpRobot deleteTidyUpRobotById(UUID id);

}
