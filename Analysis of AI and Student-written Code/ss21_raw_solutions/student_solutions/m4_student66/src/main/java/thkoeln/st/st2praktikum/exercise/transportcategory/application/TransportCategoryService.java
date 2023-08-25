package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.OutOfBoundsException;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;


@Service
public class TransportCategoryService {

    private TransportCategoryRepository transportCategoryRepository;
    private RoomService roomService;

    @Autowired
    public TransportCategoryService(TransportCategoryRepository transportCategoryRepository, RoomService roomService){
        this.transportCategoryRepository = transportCategoryRepository;
        this.roomService = roomService;
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newTransportCategory = new TransportCategory(category);
        return transportCategoryRepository.save(newTransportCategory).getUuid();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceRoomId, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        Room sourceRoom = roomService.getRoomRepository().findByUuid(sourceRoomId);
        Room destinationRoom = roomService.getRoomRepository().findByUuid(destinationRoomId);
        TransportCategory transportCategory = transportCategoryRepository.findByUuid(transportCategoryId);
        if(sourceRoom.isInsideLimit(sourcePoint) && destinationRoom.isInsideLimit(destinationPoint)) {
            Connection newConnection = new Connection(sourceRoom, sourcePoint, destinationRoom, destinationPoint);
            sourceRoom.getUseAbles().add(newConnection);
            transportCategory.addConnection(newConnection);
            return transportCategoryRepository.save(transportCategory).getUuid();
        }else {
            throw new OutOfBoundsException("Connection is placed out of bounds.");
        }
    }
}
