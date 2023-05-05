package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.repo.SpaceRepositry;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.repo.ConnectionRepositry;
import thkoeln.st.st2praktikum.exercise.transportcategory.repo.TransportCategoryRepositry;


import java.util.Optional;
import java.util.UUID;


@Service
public class TransportCategoryService {
    @Autowired
    TransportCategoryRepositry transportCategoryRepositry;
    @Autowired
    SpaceRepositry spaceRepositry;
    @Autowired
    ConnectionRepositry connectionRepositry;


    /**
     * This method adds a transport category
     *
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategoryRepositry.save(transportCategory);
        return transportCategory.getTransportCategoryId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     *
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId       the ID of the space where the entry point of the connection is located
     * @param sourcePoint         the point of the entry point
     * @param destinationSpaceId  the ID of the space where the exit point of the connection is located
     * @param destinationPoint    the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceSpaceId,
                              Point sourcePoint,
                              UUID destinationSpaceId,
                              Point destinationPoint) {

        Optional<TransportCategory> transportCategory = transportCategoryRepositry.findById(transportCategoryId);
        Optional<Space> sourceSpace = spaceRepositry.findById(sourceSpaceId);
        Optional<Space> destinationSpace = spaceRepositry.findById(destinationSpaceId);
        if (transportCategory.isPresent() && sourceSpace.isPresent() && destinationSpace.isPresent())
            if (sourceSpace.get().isCoordinateExist(sourcePoint) && destinationSpace.get().isCoordinateExist(destinationPoint)){
                Connection connection=new Connection(sourceSpaceId,sourcePoint,destinationSpaceId,destinationPoint);
                connectionRepositry.save(connection);
                return connection.getConnectionId();



            }

                throw new UnsupportedOperationException();
    }
}
