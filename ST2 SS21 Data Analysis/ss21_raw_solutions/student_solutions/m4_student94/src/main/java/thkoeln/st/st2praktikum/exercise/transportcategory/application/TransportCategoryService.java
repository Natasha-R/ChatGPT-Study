package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;


@Service
public class TransportCategoryService {
    @Autowired
    TransportCategoryRepository transportCategoryRepository;
    @Autowired
    ConnectionRepository connectionRepository;
    @Autowired
    SpaceRepository spaceRepository;
    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {

        TransportCategory transportCategory= new TransportCategory(category);
        transportCategoryRepository.save(transportCategory);
        return transportCategory.getTransportCategoryId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceSpaceId,
                              Coordinate sourceCoordinate,
                              UUID destinationSpaceId,
                              Coordinate destinationCoordinate) {
        TransportCategory transportCategory= transportCategoryRepository.findById(transportCategoryId).get();
        Connection connection= new Connection(transportCategory,sourceSpaceId,sourceCoordinate,destinationSpaceId,destinationCoordinate);

        if (getSpaceShip(sourceSpaceId).getHeight() <= sourceCoordinate.getX() || getSpaceShip(sourceSpaceId).getWidth() <= sourceCoordinate.getY())
            throw new RuntimeException();
        else if (getSpaceShip(destinationSpaceId).getHeight()<= destinationCoordinate.getX() || getSpaceShip(destinationSpaceId).getWidth() <= destinationCoordinate.getY())
            throw new RuntimeException();
        else
            transportCategory.addConnection(connection);
           // connectionRepository.save(connection);
        return connection.getConnectionId();
    }

    public Space getSpaceShip(UUID spaceId){
        return spaceRepository.findById(spaceId).get();}
}
