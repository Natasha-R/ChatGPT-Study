package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Task;

import java.util.UUID;

public interface TaskRepository extends CrudRepository<Task, UUID> {
}
