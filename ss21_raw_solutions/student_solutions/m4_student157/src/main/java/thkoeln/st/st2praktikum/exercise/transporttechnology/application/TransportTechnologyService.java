package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.repository.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    private List<TransportTechnology> transportTechnologies = new ArrayList<>();
    private SpaceShipDeckRepository spaceShipDeckRepository;

    @Autowired
    public TransportTechnologyService(SpaceShipDeckRepository spaceShipDeckRepository) {
        this.spaceShipDeckRepository = spaceShipDeckRepository;
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        this.transportTechnologies.add(transportTechnology);
        return transportTechnology.getUuid();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceSpaceShipDeckId,
                              Vector2D sourceVector2D,
                              UUID destinationSpaceShipDeckId,
                              Vector2D destinationVector2D) {
        Connection connection = new Connection(sourceSpaceShipDeckId, sourceVector2D, destinationSpaceShipDeckId, destinationVector2D, transportTechnologyId);
        Optional<SpaceShipDeck> optionalSpaceShipDeck = this.spaceShipDeckRepository.findById(sourceSpaceShipDeckId);
        if(optionalSpaceShipDeck.isEmpty()) throw new RuntimeException();
        SpaceShipDeck spaceShipDeck = optionalSpaceShipDeck.get();
        spaceShipDeck.addConnection(connection);

        return connection.getId();
    }
}
