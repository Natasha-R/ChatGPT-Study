package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportCategoryRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;


import java.util.UUID;


@Service
public class TransportCategoryService {
    @Autowired
    private TransportCategoryRepository transportCategoryRepository;
    @Autowired
    private RoomRepository roomRepository;
    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategoryRepository.save(transportCategory);

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
        TransportCategory transportCategory = transportCategoryRepository.getTransportCategoryByid(transportCategoryId);


        UUID connectionID = roomRepository.getRoomByid(sourceRoomId).addConnection(transportCategory, sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);
        if (connectionID == null) {
            throw new UnsupportedOperationException();
        } else
            return connectionID;
    }
}
