package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.*;


@Service
public class TransportCategoryService {

    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    private RoomRepository roomRepository;

    private List<Connection> connections;
    private HashMap<UUID, TransportCategory> transportCategories;
    private HashMap<UUID, Room> rooms;

    public TransportCategoryService()
    {
        connections = new ArrayList<>();
        transportCategories = new HashMap<>();
        rooms = new HashMap<>();
    }


    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category)
    {
        if(category == null)
        {
            throw new IllegalArgumentException();
        }

        UUID uuid = UUID.randomUUID();
        TransportCategory transportCategory = new TransportCategory(uuid, category);
        transportCategories.put(uuid, transportCategory);
        transportCategoryRepository.save(transportCategory);

        return uuid;
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
    public java.util.UUID addConnection(java.util.UUID transportCategoryId, java.util.UUID sourceRoomId, Vector2D sourceVector2D, java.util.UUID destinationRoomId, Vector2D destinationVector2D)
    {
        if(sourceRoomId == null || sourceVector2D == null || destinationRoomId == null || destinationVector2D == null
                || roomRepository.findById(sourceRoomId).isEmpty() || roomRepository.findById(destinationRoomId).isEmpty())
        {
            throw new IllegalArgumentException("One or more arguments are null!");
        }

        if(!(isVectorInBounds(sourceRoomId, sourceVector2D) && isVectorInBounds(destinationRoomId, destinationVector2D)))
        {
            throw new IllegalArgumentException("One or more vectors are out of bounds!");
        }

        var rooms_iterator = roomRepository.findAll();
        for(Room room : rooms_iterator) {
            rooms.put(room.getRoomId(), room);
        }

        if(transportCategories.get(transportCategoryId).getConnections() == null)
        {
            transportCategories.get(transportCategoryId).setConnections(new LinkedList<>());
        }

        Connection connection = new Connection(transportCategoryId, sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);
        transportCategories.get(transportCategoryId).getConnections().add(connection);

        TransportCategory transportCategory = transportCategories.get(transportCategoryId);

        Room room = rooms.get(sourceRoomId);
        room.setGridCellToConnection(sourceVector2D.getX(), sourceVector2D.getY(), connection);
        Room newRoom = new Room(room.getRoomId(), room.getWidth(), room.getHeight(), room.getGridAsList());
        roomRepository.deleteById(room.getRoomId());
        roomRepository.save(newRoom);

        //transportCategoryRepository.delete(transportCategory);
        //transportCategory.getConnections().add(connection);
        transportCategoryRepository.save(transportCategory);
        return UUID.randomUUID();
    }

    private boolean isVectorInBounds(java.util.UUID roomId, Vector2D vector2D)
    {
        if(vector2D.getX() >= 0 && vector2D.getX() < roomRepository.findById(roomId).get().getWidth()
                && vector2D.getY() >= 0 && vector2D.getY() < roomRepository.findById(roomId).get().getHeight())
        {
            return true;
        }

        return false;
    }
}
