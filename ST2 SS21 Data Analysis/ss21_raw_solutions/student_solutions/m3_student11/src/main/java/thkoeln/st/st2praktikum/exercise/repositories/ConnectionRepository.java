package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;
import thkoeln.st.st2praktikum.exercise.Connection;

public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
}
