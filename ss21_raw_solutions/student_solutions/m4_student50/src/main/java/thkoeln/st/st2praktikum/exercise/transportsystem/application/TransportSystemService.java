package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Zone;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;



import java.util.UUID;


@Service
public class TransportSystemService {
    @Autowired
    private Zone zone;

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        return zone.createTransportTechnology(system);
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceRoomId,
                              Coordinate sourceCoordinate,
                              UUID destinationRoomId,
                              Coordinate destinationCoordinate) {
        return zone.createConnection(sourceRoomId,
                sourceCoordinate,
                destinationRoomId,
                destinationCoordinate);
    }
}
