package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.repositories.ShipDeckRepository;

import java.util.UUID;


@Service
public class TransportTechnologyService {

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;
    @Autowired
    private ShipDeckRepository shipDeckRepository;

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getId();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport technology. Connections only work in one direction.
     *
     * @param transportTechnologyId      the transport technology which is used by the connection
     * @param sourceSpaceShipDeckId      the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceCoordinate           the coordinate of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationCoordinate      the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        TransportTechnology transportCategory = transportTechnologyRepository.getTransportTechnologyByid(transportTechnologyId);


        UUID connectionID = shipDeckRepository.getShipDeckByid(sourceSpaceShipDeckId).addConnection(transportCategory, sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
        if (connectionID == null) {
            throw new UnsupportedOperationException();
        } else
            return connectionID;
    }
}
