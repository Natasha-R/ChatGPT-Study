package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {

    default ArrayList<Connection> getConnectionsBySourceDeckID(UUID sourceSpaceShipId) {
        ArrayList<Connection> allConnections = this.getAllConnections();

        return allConnections.stream()
                .filter(c -> c.getSourceSpaceShipDeckId().equals(sourceSpaceShipId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Connection> getAllConnections() {
        ArrayList<Connection> allConnections = new ArrayList<>();
        for (TransportSystem transportSystem : this.findAll()) {
            allConnections.addAll(transportSystem.getConnections());
        }
        return allConnections;
    }
}
