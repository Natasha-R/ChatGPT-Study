package thkoeln.st.st2praktikum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import java.util.UUID;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
    public Connection getConnectionByid(UUID id);
}
