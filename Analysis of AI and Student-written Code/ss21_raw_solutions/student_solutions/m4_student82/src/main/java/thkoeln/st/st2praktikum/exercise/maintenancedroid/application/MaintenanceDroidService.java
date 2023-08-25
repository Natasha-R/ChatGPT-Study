package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Exception.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;


import java.util.UUID;


@Service
public class MaintenanceDroidService {

    @Getter
    private final MaintenanceDroidRepository droids;

    @Getter
    private final SpaceShipDeckService decks;

    @Autowired
    public MaintenanceDroidService(MaintenanceDroidRepository droids, SpaceShipDeckService decks) {
        this.droids = droids;
        this.decks = decks;
    }
    
    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        final MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        this.droids.save(maintenanceDroid);
        return maintenanceDroid.getMaintenanceDroidId();
    }

    /**
     * This method lets the maintenance droid execute a order.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        MaintenanceDroid maintenanceDroid = this.droids.findById(maintenanceDroidId).get();
        OrderType orderType = order.getOrderType();
        maintenanceDroid.getOrders().add(order);
        switch(orderType){
            case WEST:
            case NORTH:
            case SOUTH:
            case EAST:
                return Order.orderWithPower(order, maintenanceDroid, decks.getDecks());

            case ENTER:
            case TRANSPORT:
                return Order.orderWithUUID(order, maintenanceDroid, decks.getDecks());
            default:
                return false;
        }
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        try {
            return droids.findById(maintenanceDroidId).get().getSpaceShipDeckId();
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * This method returns the coordinate a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinate of the maintenance droid
     */
    public Coordinate getMaintenanceDroidCoordinate(UUID maintenanceDroidId){
        return this.droids.findById(maintenanceDroidId).get().getCoordinate();
    }
}
