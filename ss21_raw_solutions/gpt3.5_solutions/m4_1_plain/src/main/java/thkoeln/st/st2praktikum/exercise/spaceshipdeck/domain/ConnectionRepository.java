package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConnectionRepository {
    void save(Connection connection);
    Optional<Connection> findById(UUID id);
    void delete(Connection connection);
    List<Connection> findAll();
    List<Connection> findBySourceSpaceShipDeckId(UUID sourceSpaceShipDeckId);
    List<Connection> findByDestinationSpaceShipDeckId(UUID destinationSpaceShipDeckId);
    List<Connection> findByTransportCategory(String transportCategory);
}
