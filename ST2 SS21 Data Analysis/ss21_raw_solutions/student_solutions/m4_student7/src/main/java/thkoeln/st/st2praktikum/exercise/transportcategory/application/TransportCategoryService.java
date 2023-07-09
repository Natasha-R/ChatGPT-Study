package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


@Service
public class TransportCategoryService {

    @Setter
    private RoomService roomService;
    @Setter
    private TidyUpRobotService tidyUpRobotService;

    private List<TransportCategory> transportCategories = new ArrayList<TransportCategory>();
    private List<Connection> connections = new ArrayList<Connection>();

    private TransportCategoryRepository transportCategoryRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public TransportCategoryService(TransportCategoryRepository transportCategoryRepository,
                                    ConnectionRepository connectionRepository) {
        this.transportCategoryRepository = transportCategoryRepository;
        this.connectionRepository = connectionRepository;
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategories.add(transportCategory);
        persistNewData();
        return transportCategory.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceRoomId,
                              Vector2D sourceVector2D,
                              UUID destinationRoomId,
                              Vector2D destinationVector2D) {
        TransportCategory transportCategory = getTransportCategoryByTransportCategoryId(transportCategoryId);
        Room sourceRoom = roomService.getRoomByRoomId(sourceRoomId);
        Room destinationRoom = roomService.getRoomByRoomId(destinationRoomId);
        Connection connection = new Connection(transportCategory, sourceRoom, sourceVector2D, destinationRoom, destinationVector2D);

        if(sourceRoom.isCoordinateInBounds(sourceVector2D) && destinationRoom.isCoordinateInBounds(destinationVector2D)) {
            connections.add(connection);
            transportCategories.add(transportCategory);
            persistNewData();
            return connection.getId();
        }else {
            throw new UnsupportedOperationException("connection out of bounds");
        }
    }

    public Boolean transportRobot(TidyUpRobot tidyUpRobot, Task task){
        Room destinationRoom = roomService.getRoomByRoomId(task.getGridId());

        Optional<Connection> possibleConnections = findPossibleConnections(destinationRoom, tidyUpRobot);

        if(possibleConnections.isPresent() && tidyUpRobotService.destinationNotOccupied(destinationRoom, possibleConnections.get().getSourceCoordinates(), tidyUpRobot)){
            tidyUpRobot.setRoom(destinationRoom);
            tidyUpRobot.move(possibleConnections.get().getDestinationCoordinates());
            destinationRoom.addBlocker(tidyUpRobot);
            possibleConnections.get().getSourceRoom().removeBlocker(tidyUpRobot);

            persistNewData();
            return true;
        }else
            return false;
    }

    private Optional<Connection> findPossibleConnections(Room destinationRoom, TidyUpRobot tidyUpRobot){
        Stream<Connection> possibleConnectionsStream = connections.stream();

        possibleConnectionsStream = possibleConnectionsStream.filter(findConnection ->
                findConnection.getSourceCoordinates().equals(tidyUpRobot.getVector2D())
                        && findConnection.getSourceRoom() == tidyUpRobot.getRoom()
                        && findConnection.getDestinationRoom() == destinationRoom);

        return possibleConnectionsStream.findFirst();
    }

    public void persistNewData(){
        transportCategoryRepository.deleteAll();
        connectionRepository.deleteAll();

        transportCategories.forEach(transportCategory -> transportCategoryRepository.save(transportCategory));
        connections.forEach(connection -> connectionRepository.save(connection));
    }

    private TransportCategory getTransportCategoryByTransportCategoryId(UUID transportCategoryId){
        return transportCategories.stream().filter(findTransportCategory -> findTransportCategory.getId().equals(transportCategoryId)).findAny().orElse(null);
    }


}
