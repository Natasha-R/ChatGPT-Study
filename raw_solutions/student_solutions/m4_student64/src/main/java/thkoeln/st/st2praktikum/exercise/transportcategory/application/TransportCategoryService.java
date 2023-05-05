package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceService;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Service
public class TransportCategoryService {
    @Autowired
    TransportCategoryRepository transportCategoryRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    SpaceService spaceService;

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newTransportCategory = new TransportCategory(category);
        this.transportCategoryRepository.save(newTransportCategory);

        return newTransportCategory.getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceSpaceId,
                              Point sourcePoint,
                              UUID destinationSpaceId,
                              Point destinationPoint) {
        TransportCategory transportCategory = this.transportCategoryRepository.findById(transportCategoryId).orElseThrow();
        List<Connection> transportCategoryConnections = transportCategory.getConnections();

        Space sourceSpace = this.spaceService.getSpaceById(sourceSpaceId);
        Space destinationSpace = this.spaceService.getSpaceById(destinationSpaceId);

        Connection newConnection = new Connection(sourceSpace, sourcePoint, destinationSpace, destinationPoint);
        this.connectionRepository.save(newConnection);

        transportCategoryConnections.add(newConnection);
        this.transportCategoryRepository.save(transportCategory);

        return newConnection.getId();
    }

    /**
     * @param startSpaceId Cool
     */
    public Iterator<Connection> getConnectionByStartSpaceId(UUID startSpaceId)
    {
        return this.connectionRepository.findByStartSpaceId(startSpaceId).iterator();
    }
}
