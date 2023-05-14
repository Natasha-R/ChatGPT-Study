package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Connection;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;


@Service
public class TransportCategoryService {

    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;

    @Autowired
    private ConnectionRepository connectionRepository;
    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory();
        transportCategory.setCategory(category);

        transportCategoryRepository.save(transportCategory);

        return transportCategory.getId();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceSpaceShipDeckId,
                              Vector2D sourceVector2D,
                              UUID destinationSpaceShipDeckId,
                              Vector2D destinationVector2D) {
        Connection connection = new Connection();

        connection.setTransportCategoryId(transportCategoryId);
        connection.setSourceSpaceShipDeckId(sourceSpaceShipDeckId);
        connection.setSourceVector2D(sourceVector2D);
        connection.setDestinationSpaceShipDeckId(destinationSpaceShipDeckId);
        connection.setDestinationVector2D(destinationVector2D);

        spaceShipDeckRepository.findById(sourceSpaceShipDeckId).get().addConnections(connection);

        connectionRepository.save(connection);

        return connection.getId();
    }
}
