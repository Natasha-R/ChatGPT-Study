package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;



import java.util.UUID;


@Service
public class TransportTechnologyService {
    
    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,
                              UUID sourceRoomId,
                              Coordinate sourceCoordinate,
                              UUID destinationRoomId,
                              Coordinate destinationCoordinate) {
        throw new UnsupportedOperationException();
    }
}
