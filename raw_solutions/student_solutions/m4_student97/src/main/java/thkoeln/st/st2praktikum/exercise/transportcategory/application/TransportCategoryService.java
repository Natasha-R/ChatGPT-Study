package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.repository.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.repository.TransportCategoryRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class TransportCategoryService {

    private final TransportCategoryRepository transportCategoryRepository;
    private final RoomRepository roomRepository;
    private final ConnectionRepository connectionRepository;

    @Autowired
    public TransportCategoryService(TransportCategoryRepository transportCategoryRepository,
                                    ConnectionRepository connectionRepository,
                                    RoomRepository roomRepository) {
        this.transportCategoryRepository = transportCategoryRepository;
        this.connectionRepository = connectionRepository;
        this.roomRepository = roomRepository;
    }
    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newTransportCategory = new TransportCategory(category);
        return transportCategoryRepository.save(newTransportCategory).getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourcePoint the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceRoomId,
                              Point sourcePoint,
                              UUID destinationRoomId,
                              Point destinationPoint) {
        Room sourceRoom = roomRepository.findById(sourceRoomId).get();
        Room destinationRoom = roomRepository.findById(destinationRoomId).get();
        TransportCategory transportCategory = transportCategoryRepository.findById(transportCategoryId).get();
        Connection newConnection = new Connection(sourcePoint, sourceRoom, destinationPoint, destinationRoom);

        transportCategory.getConnections().add(newConnection);
        return transportCategoryRepository.save(transportCategory).getId();
    }

    public Optional<Connection> getConnection(Room sourceRoom, Point sourcePosition, UUID destinationRoomId) {
        Room destinationRoom = roomRepository.findById(destinationRoomId).get();
        return connectionRepository.findConnectionsByStartRoomAndStartPositionAndEndRoom(sourceRoom, sourcePosition, destinationRoom);
    }
}
