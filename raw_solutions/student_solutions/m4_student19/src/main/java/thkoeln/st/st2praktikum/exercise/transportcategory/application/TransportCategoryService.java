package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportCategoryRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;


import java.util.UUID;


@Service
public class TransportCategoryService {

    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newCategory = new TransportCategory(category);
        this.transportCategoryRepository.save(newCategory);
        return newCategory.getId();
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
        if (this.transportCategoryRepository.findById(transportCategoryId).isEmpty())
            throw new IllegalArgumentException("There is no transportCategory existing with the given transportCategoryId: " + transportCategoryId);
        if (this.spaceRepository.findById(sourceSpaceId).isEmpty())
            throw new IllegalArgumentException("There is no space existing with the given sourceSpaceId: " + sourceSpaceId);
        if (this.spaceRepository.findById(destinationSpaceId).isEmpty())
            throw new IllegalArgumentException("There is no space existing with the given destinationSpaceId: " + destinationSpaceId);
        if (this.spaceRepository.findById(sourceSpaceId).get().getSpaceHeight() < sourceVector2D.getY() ||
                this.spaceRepository.findById(sourceSpaceId).get().getSpaceWidth() < sourceVector2D.getX() ||
                this.spaceRepository.findById(destinationSpaceId).get().getSpaceHeight() < destinationVector2D.getY() ||
                this.spaceRepository.findById(destinationSpaceId).get().getSpaceWidth() < destinationVector2D.getX())
            throw new IllegalArgumentException("Given Vectors are out of bounds for the given Spaces.");

        Connection newConnection = new Connection(sourceSpaceId, sourceVector2D, destinationSpaceId, destinationVector2D);
        this.connectionRepository.save(newConnection);
        this.transportCategoryRepository.findById(transportCategoryId).get().addConnection(newConnection);
        return newConnection.getId();
    }
}
