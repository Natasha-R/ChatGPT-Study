package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleanerRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;


@Service
public class TransportCategoryService {

    @Autowired
    CleanerRepository cleanerRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

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
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceSpaceId,
                              Vector2D sourceVector2D,
                              UUID destinationSpaceId,
                              Vector2D destinationVector2D)
    {
        Space sourceSpace = spaceRepository.findById(sourceSpaceId).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        Space destinationSpace = spaceRepository.findById(destinationSpaceId).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        if (sourceSpace.fieldExists(sourceVector2D.getX(), sourceVector2D.getY()) && destinationSpace.fieldExists(destinationVector2D.getX(), destinationVector2D.getY()))
        {
            Connection connection = new Connection(transportCategoryId, sourceSpaceId, sourceVector2D, destinationSpaceId, destinationVector2D);



            sourceSpace.getField(sourceVector2D.getX(), sourceVector2D.getY()).setConnection(connection);
            //spaceRepository.deleteById(sourceSpace.getUuid());
            spaceRepository.save(sourceSpace);
            return connection.getUuid();
        }
        else
        {
            throw new IndexOutOfBoundsException("There is no Field with this coordinates");
        }
    }
}
