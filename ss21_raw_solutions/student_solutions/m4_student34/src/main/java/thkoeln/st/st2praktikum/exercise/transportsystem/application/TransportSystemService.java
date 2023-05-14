package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystemRepository;


import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@AllArgsConstructor
@Transactional
public class TransportSystemService {

    private final TransportSystemRepository transportSystemRepository;
    private final SpaceShipDeckRepository spaceShipDeckRepository;

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        var transportSystem = new TransportSystem(system);
        return this.transportSystemRepository.save(transportSystem).getId();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceSpaceShipDeckId,
                              Coordinate sourceCoordinate,
                              UUID destinationSpaceShipDeckId,
                              Coordinate destinationCoordinate) {
        var transportSystem = this.transportSystemRepository.findById(transportSystemId)
                .orElseThrow(NoSuchElementException::new);
        var sourceSpaceShipDeck = this.spaceShipDeckRepository.findById(sourceSpaceShipDeckId)
                .orElseThrow(NoSuchElementException::new);
        var destinationSpaceShipDeck = this.spaceShipDeckRepository.findById(destinationSpaceShipDeckId)
                .orElseThrow(NoSuchElementException::new);
        var connection = new Connection(
                sourceSpaceShipDeck,
                sourceCoordinate,
                destinationSpaceShipDeck,
                destinationCoordinate
        );
        if(!transportSystem.addConnection(connection)) {
            throw new IllegalStateException();
        }
        this.transportSystemRepository.save(transportSystem);
        return connection.getId();
    }
}
