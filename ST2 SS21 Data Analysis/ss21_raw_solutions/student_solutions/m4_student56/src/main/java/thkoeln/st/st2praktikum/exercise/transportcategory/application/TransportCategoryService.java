package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;


import java.util.UUID;


@Service
public class TransportCategoryService {


    @Autowired
    ConnectionRepository connectionRepository;
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        return UUID.randomUUID();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceRoomId,
                              Coordinate sourceCoordinate,
                              UUID destinationRoomId,
                              Coordinate destinationCoordinate) {


        Connection connection = new Connection();
        connection.setSourceRoomID(sourceRoomId);
        connection.setDestinationRoomID(destinationRoomId);
        connection.setSourceCoordinates(sourceCoordinate);
        connection.setDestinationCoordinates(destinationCoordinate);
        connectionRepository.save(connection);
        return connection.getConnectionID();
    }
}
