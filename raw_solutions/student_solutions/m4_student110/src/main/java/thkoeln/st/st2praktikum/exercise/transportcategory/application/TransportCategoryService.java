package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.transportcategory.TransportCategory;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TransportCategoryService extends TidyUpRobotService {



    public List<TransportCategory> transportCategories = new ArrayList<>();
    public List<Connection> connections = new ArrayList<>();

    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newTransportCategory = new TransportCategory(category);
        transportCategories.add(newTransportCategory);
        return newTransportCategory.getTransportCategoryId();
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

        Connection newConnection = new Connection(transportCategoryId,sourceRoomId,sourceCoordinate,destinationRoomId,destinationCoordinate);
        connections.add(newConnection);

        return newConnection.getConnectionId();


    }
}
