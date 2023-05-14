package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Connection;

import java.util.UUID;
public interface ConnectionRepository extends CrudRepository<Connection, UUID>{
    Connection findConnectionById(UUID connection);

}
