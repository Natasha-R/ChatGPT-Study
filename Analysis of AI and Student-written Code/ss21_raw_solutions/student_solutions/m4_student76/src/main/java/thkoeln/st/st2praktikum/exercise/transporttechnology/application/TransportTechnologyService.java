package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.World;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;



import java.util.UUID;


@Service
public class TransportTechnologyService {

    @Autowired
    private World world;
    
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return world.createTransportTechnology(technology);
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceSpaceShipDeckId,
                              Point sourcePoint,
                              UUID destinationSpaceShipDeckId,
                              Point destinationPoint) {
        return world.createConnection(sourceSpaceShipDeckId,
                sourcePoint,
                destinationSpaceShipDeckId,destinationPoint);
    }
}
