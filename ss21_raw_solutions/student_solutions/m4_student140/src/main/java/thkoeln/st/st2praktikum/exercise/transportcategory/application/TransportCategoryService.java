package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Connection;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.repository.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repository.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.repository.TransportCategoryRepository;


import javax.persistence.EntityNotFoundException;
import java.util.UUID;


@Service
public class TransportCategoryService {

    @Autowired
    protected SpaceRepository spaceRepo;

    @Autowired
    protected ConnectionRepository connectionRepo;

    @Autowired
    protected TransportCategoryRepository transportCategoryRepo;


    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        UUID transportCategoryId = UUID.randomUUID();

        TransportCategory transportCategory = new TransportCategory( transportCategoryId , category );

        transportCategoryRepo.save( transportCategory );

        return transportCategoryId;
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

        UUID connectionId = UUID.randomUUID();

        Space sourceSpace = spaceRepo.findById( sourceSpaceId )
                .orElseThrow( () -> new EntityNotFoundException());
        Space destinationSpace = spaceRepo.findById( destinationSpaceId )
                .orElseThrow( () -> new EntityNotFoundException());

        Integer sourceCoordinateX = sourceCoordinate.getX();
        Integer destCoordinateX = destinationCoordinate.getX();
        Integer sourceCoordinateY = sourceCoordinate.getY();
        Integer destCoordinateY = destinationCoordinate.getY();

        if( sourceCoordinateX >= sourceSpace.getWidth() || sourceCoordinateY >= sourceSpace.getHeight() )
            throw new RuntimeException("source coordinates out of bounds");

        if( destCoordinateX >= destinationSpace.getWidth() || destCoordinateY >= destinationSpace.getHeight() )
            throw new RuntimeException("destination coordinates out of bounds");

        Connection connection = new Connection(connectionId,sourceSpace,destinationSpace,sourceCoordinate,destinationCoordinate);
        connectionRepo.save( connection );


        return connectionId;
    }
}
