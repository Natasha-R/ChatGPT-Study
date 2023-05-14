package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
}