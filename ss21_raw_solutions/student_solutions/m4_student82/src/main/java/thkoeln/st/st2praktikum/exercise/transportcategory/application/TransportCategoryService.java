package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Exception.NoFieldException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.DeckRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;


@Service
public class TransportCategoryService {

    @Getter
    private final TransportCategoryRepository categories;

    @Getter
    private final SpaceShipDeckService decks;


    @Autowired
    public TransportCategoryService(TransportCategoryRepository categories, SpaceShipDeckService decks) {
        this.categories = categories;
        this.decks = decks;


    }

    
    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        final TransportCategory transportCategory = new TransportCategory(category);
        this.categories.save(transportCategory);
        return transportCategory.getTransportCategorieId();
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
        try {
            Connection connection = new Connection(sourceSpaceShipDeckId, destinationSpaceShipDeckId, sourceCoordinate, destinationCoordinate);

            decks.getDecks().findById(sourceSpaceShipDeckId).get().getCoordinates().forEach(coordinate -> {


                if (coordinate.equals(sourceCoordinate)){
                    coordinate.setConnection(connection);
                    System.out.println("Gesetzte connection: " + coordinate.getConnection());
                }

            });
            TransportCategory transportCategory = categories.findById(transportCategoryId).get();
            transportCategory.getConnections().add(connection);
            return categories.save(transportCategory).getTransportCategorieId();
        } catch (NoFieldException e) {
            e.printStackTrace();
            return null;
        }
    }


}
