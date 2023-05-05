package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.StringService;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidPositionIsNull;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SquareNotFoundException;


import java.util.UUID;

@Getter
@Service
public class MaintenanceDroidService {
    @Autowired
    public MaintenanceDroidService(SpaceShipDeckRepository spaceShipDecks, MaintenanceDroidRepository maintenanceDroids) {
        this.spaceShipDecks = spaceShipDecks;
        this.maintenanceDroids = maintenanceDroids;
    }

    private final SpaceShipDeckRepository spaceShipDecks;
    private final MaintenanceDroidRepository maintenanceDroids;
    
    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        maintenanceDroids.save(maintenanceDroid);
        return maintenanceDroid.getMaintenanceDroidID();
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
     *      (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        Command command = StringService.translateOrderToCommand(order);
        MaintenanceDroid maintenanceDroid = maintenanceDroids.findById(maintenanceDroidId).get();
        maintenanceDroid.getOrders().add(order);
        try {
            if (command.isStepCommand()) {
                return maintenanceDroid.isStepCommand(command, maintenanceDroids, spaceShipDecks);
            }
            if (command.isUUIDCommand()) {
                return maintenanceDroid.isUUIDCommand(command, spaceShipDecks, maintenanceDroids);
            }
        } catch (MaintenanceDroidPositionIsNull | IllegalStateException | SquareNotFoundException e) {
//            System.out.println(e.toString());
            return false;
        }
        //        System.out.println("executeCommand Failure");
        return false;
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return maintenanceDroids.findById(maintenanceDroidId).get().getSpaceShipDeckId();
    }

    /**
     * This method returns the coordinate a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinate of the maintenance droid
     */
    public Coordinate getMaintenanceDroidCoordinate(UUID maintenanceDroidId){
        return maintenanceDroids.findById(maintenanceDroidId).get().getSquare().getCoordinates();
    }
}
