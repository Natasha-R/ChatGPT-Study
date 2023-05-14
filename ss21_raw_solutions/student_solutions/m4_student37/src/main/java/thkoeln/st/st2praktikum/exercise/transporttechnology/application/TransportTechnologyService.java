package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.extra.World;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;

import java.security.InvalidParameterException;
import java.util.UUID;


@Service
public class TransportTechnologyService {

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;
    
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        World.getInstance().addTransportTechnology(transportTechnology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getTransportId();
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
        Connector connector = new Connector(sourceVector2D,destinationSpaceShipDeckId,destinationVector2D);
        TransportTechnology transportTechnology = transportTechnologyRepository.findById(transportTechnologyId).orElseThrow(InvalidParameterException::new);
        transportTechnology.addConnector(connector);
        World.getInstance().addConnectors(transportTechnology,sourceSpaceShipDeckId,connector);
        transportTechnologyRepository.save(transportTechnology);
        return connector.getId();
    }
}
