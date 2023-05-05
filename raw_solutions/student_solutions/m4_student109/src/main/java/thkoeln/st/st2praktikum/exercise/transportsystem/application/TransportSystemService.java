package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;


import java.util.UUID;


@Service
public class TransportSystemService {

    private TransportSystemRepository transportSystemRepository;
    private SpaceShipDeckRepository spaceShipDeckRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public TransportSystemService(TransportSystemRepository transportSystemRepository, SpaceShipDeckRepository spaceShipDeckRepository, ConnectionRepository connectionRepository) {
        this.transportSystemRepository = transportSystemRepository;
        this.spaceShipDeckRepository = spaceShipDeckRepository;
        this.connectionRepository = connectionRepository;
    }

    public SpaceShipDeck SSDfindById( UUID id ) {
        SpaceShipDeck spaceShipDeck = spaceShipDeckRepository.findById( id ).orElseThrow( () ->
                new RuntimeException( "No dungeon with ID " + id + " can be found.") );
        return spaceShipDeck;
    }

    public TransportSystem TSfindById( UUID id ) {
        TransportSystem transportSystem = transportSystemRepository.findById( id ).orElseThrow( () ->
                new RuntimeException( "No dungeon with ID " + id + " can be found.") );
        return transportSystem;
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        UUID transportId = UUID.randomUUID();
        transportSystemRepository.save(new TransportSystem(transportId, system));
        return transportId;
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceSpaceShipDeckId, Vector2D sourceVector2D, UUID destinationSpaceShipDeckId, Vector2D destinationVector2D) {
        UUID connectionId = UUID.randomUUID();
            TransportSystem transportSystem = TSfindById(transportSystemId);
            SpaceShipDeck sourceSpaceShipDeck = SSDfindById(sourceSpaceShipDeckId);
                        if (sourceSpaceShipDeck.getHeight() < sourceVector2D.getY() || sourceSpaceShipDeck.getWidth() < sourceVector2D.getX()) {
                            throw new RuntimeException("Connection invalid");
                        }

               SpaceShipDeck destinationSpaceShipDeck = SSDfindById(destinationSpaceShipDeckId);
                                if (destinationSpaceShipDeck.getHeight() < sourceVector2D.getY() || destinationSpaceShipDeck.getWidth() < sourceVector2D.getX()) {
                                    throw new RuntimeException("Connection invalid");
                                }

                        Connection c = new Connection(transportSystemId, sourceSpaceShipDeckId, sourceVector2D, destinationSpaceShipDeckId, destinationVector2D, connectionId);
                        sourceSpaceShipDeck.addConnection(c);
                        connectionRepository.save(c);
        return connectionId;
    }



}
