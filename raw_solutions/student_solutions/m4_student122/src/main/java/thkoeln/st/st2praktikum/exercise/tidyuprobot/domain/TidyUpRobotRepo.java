package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TidyUpRobotRepo extends JpaRepository<TidyUpRobot,UUID> {
    TidyUpRobot findTidyUpRobotById(UUID id);
    void deleteTidyUpRobotById(UUID id);

}
