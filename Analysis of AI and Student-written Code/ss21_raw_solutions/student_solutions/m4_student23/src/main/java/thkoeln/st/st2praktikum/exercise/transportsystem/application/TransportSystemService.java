package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.repositories.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransportSystemService {

    private final TransportSystemRepository transportSystemRepository;


    @Autowired
    public TransportSystemService(TransportSystemRepository transportSystemRepository) {
        this.transportSystemRepository = transportSystemRepository;
    }

    /**
     * This method adds a transport system
     *
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        this.transportSystemRepository.save(transportSystem);
        return transportSystem.getUuid();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport system. Connections only work in one direction.
     *
     * @param transportSystemId          the transport system which is used by the connection
     * @param sourceSpaceShipDeckId      the ID of the spaceship deck where the entry point of the connection is located
     * @param sourcePoint                the point of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationPoint           the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceSpaceShipDeckId,
                              Point sourcePoint,
                              UUID destinationSpaceShipDeckId,
                              Point destinationPoint) {
        TransportSystem transportSystem = this.getTransportSystemByUUID(transportSystemId);
        Connection newConnection = new Connection(sourceSpaceShipDeckId, sourcePoint, destinationSpaceShipDeckId, destinationPoint);

        return transportSystem.addConnection(newConnection);
    }

    private TransportSystem getTransportSystemByUUID(UUID transportSystemId) {
        return this.transportSystemRepository.findById(transportSystemId).orElseThrow(() ->
                new InvalidParameterException("No TransportSystem Matches the given UUID"));
    }
}
