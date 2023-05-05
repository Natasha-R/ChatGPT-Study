package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;


import java.util.UUID;


@Service
public class TransportCategoryService {

    private final TransportCategoryRepository transportCategoryRepository;
    private final ConnectionRepository connectionRepository;
    private final SpaceRepository spaceRepository;

    @Autowired
    public TransportCategoryService(
            TransportCategoryRepository transportCategoryRepository,
            ConnectionRepository connectionRepository,
            SpaceRepository spaceRepository
    ){
        this.transportCategoryRepository = transportCategoryRepository;
        this.connectionRepository = connectionRepository;
        this.spaceRepository = spaceRepository;
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transport = new TransportCategory(category);

        return this.transportCategoryRepository.save(transport).getId();
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
        Connection connection = new Connection(
                this.spaceRepository.findById(sourceSpaceId).get(),
                sourceCoordinate,this.spaceRepository.findById(destinationSpaceId).get()
                ,destinationCoordinate);


        this.transportCategoryRepository.findById(transportCategoryId).get().addConnection(connection);
        return this.connectionRepository.save(connection).getId();
    }
}
