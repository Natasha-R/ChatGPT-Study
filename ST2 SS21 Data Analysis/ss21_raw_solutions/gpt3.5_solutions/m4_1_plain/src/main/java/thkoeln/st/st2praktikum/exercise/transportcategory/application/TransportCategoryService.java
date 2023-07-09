package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Connection;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.ConnectionRepository;

import java.util.Optional;
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
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategoryRepository.save(transportCategory);
        return transportCategory.getId();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(long transportCategoryId, UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        TransportCategory transportCategory = transportCategoryRepository.findById(transportCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transport category ID"));

        SpaceShipDeck sourceDeck = spaceShipDeckRepository.findById(sourceSpaceShipDeckId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid source spaceship deck ID"));

        SpaceShipDeck destinationDeck = spaceShipDeckRepository.findById(destinationSpaceShipDeckId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid destination spaceship deck ID"));

        String connectionString = String.format("%s,%s,%s,%s,%s,%s",
                UUID.randomUUID(),
                sourceSpaceShipDeckId,
                sourceCoordinate.toString(),
                destinationSpaceShipDeckId,
                destinationCoordinate.toString(),
                transportCategory.getName());

        Connection connection = Connection.parseConnectionString(connectionString);
        connectionRepository.save(connection);
        return connection.getId();
    }


}
