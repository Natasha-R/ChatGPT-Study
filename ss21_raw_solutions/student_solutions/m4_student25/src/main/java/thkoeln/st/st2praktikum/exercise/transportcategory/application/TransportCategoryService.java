package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SquareNotFoundException;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoriesRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;


import java.util.UUID;


@Service
public class TransportCategoryService {

    private final TransportCategoriesRepository transportCategories;
    private final SpaceShipDeckRepository spaceShipDecks;
    @Autowired
    public TransportCategoryService(TransportCategoriesRepository transportCategories, SpaceShipDeckRepository spaceShipDecks) {
        this.transportCategories = transportCategories;
        this.spaceShipDecks = spaceShipDecks;
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategories.save(transportCategory);
        return transportCategory.getTransportCategoryUUID();
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
    public UUID addConnection(UUID transportCategoryId,
                              UUID sourceSpaceShipDeckId,
                              Coordinate sourceCoordinate,
                              UUID destinationSpaceShipDeckId,
                              Coordinate destinationCoordinate) {
        Connection connection = new Connection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate, transportCategoryId);
        try {
            spaceShipDecks.findById(sourceSpaceShipDeckId).get().findSquareAt(sourceCoordinate).setConnection(connection);
            return connection.getConnectionID();
        } catch (SquareNotFoundException e) {
//            System.out.println(e.toString());
            return null;
        }
    }
}
