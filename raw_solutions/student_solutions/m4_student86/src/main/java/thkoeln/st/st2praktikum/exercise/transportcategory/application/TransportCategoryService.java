package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class TransportCategoryService {

    private TransportCategoryRepository transportCategoryRepo;
    private SpaceRepository spaceRepo;

    public TransportCategory findById (UUID id) {
        TransportCategory category = transportCategoryRepo.findById(id).orElseThrow( () ->
            new RuntimeException("TransportCategory " + id + " existiert nicht") );
        return category;
    }

    public Space findSpaceById(UUID id ) {
        Space space = spaceRepo.findById( id ).orElseThrow( () ->
                new RuntimeException( "Space " + id + " existiert nicht") );
        return space;
    }

    @Autowired
    public TransportCategoryService (
            SpaceRepository spaceRepo,
            TransportCategoryRepository transportCategoryRepo) {
        this.spaceRepo = spaceRepo;
        this.transportCategoryRepo = transportCategoryRepo;
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory (String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        UUID categoryId = transportCategory.getId();
        transportCategoryRepo.save(transportCategory);
        return categoryId;
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
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2D) {
        TransportCategory category = findById(transportCategoryId);
        Space sourceSpace = findSpaceById(sourceSpaceId);
        Space destinationSpace = findSpaceById(destinationSpaceId);

        Connection connection = new Connection(sourceSpace, sourceVector2D,
                destinationSpace, destinationVector2D);

        UUID connectionId = connection.getId();
        category.addConnection(connection);

        return connectionId;
    }
}
