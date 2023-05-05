package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepo;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepo;


import javax.persistence.EntityManager;
import java.util.UUID;


@Service
@AllArgsConstructor
public class TransportCategoryService {


    private final TransportCategoryRepo transportCategoryRepo;
    private final RoomRepo roomRepo;
    private final EntityManager entityManager;
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategoryRepo.save(transportCategory);
        System.out.println("TransportCategory Added "+transportCategory.getCategory()+" With ID "+ transportCategory.getId());
        return transportCategory.getId();
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
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceRoomId,
                              Point sourcePoint,
                              UUID destinationRoomId,
                              Point destinationPoint) {
        Room room1 = roomRepo.findRoomById(sourceRoomId);
        Room room2 = roomRepo.findRoomById(destinationRoomId);
        if(room1==null || room2==null){
            throw new RuntimeException("One or both of the rooms are not found!");
        }

        if(     sourcePoint.getX() > room1.getWidth() ||
                sourcePoint.getY() > room1.getHeight() ||
                destinationPoint.getX() > room2.getWidth() ||
                destinationPoint.getY() > room2.getHeight() ||
                destinationPoint.getX() < 0 ||
                destinationPoint.getY() < 0 ||
                sourcePoint.getX() < 0 ||
                sourcePoint.getY() < 0
        ){
            throw new RuntimeException();
        }

        TransportCategory transportCategory = transportCategoryRepo.findTransportCategoriesById(transportCategoryId);
        Connection connection = new Connection(sourceRoomId,sourcePoint,destinationRoomId,destinationPoint,transportCategory);

        room1.setConnection(connection);
        System.out.println(connection.getId());
        System.out.println(connection.getCategory().getCategory());
        entityManager.persist(connection);


        return connection.getId();
    }



}
