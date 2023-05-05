package thkoeln.st.st2praktikum.exercise.Repositories;

import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.Connection;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransportSystemRepository {
    List<Connection> findAllConnections();
    Connection findById (UUID id);
}
