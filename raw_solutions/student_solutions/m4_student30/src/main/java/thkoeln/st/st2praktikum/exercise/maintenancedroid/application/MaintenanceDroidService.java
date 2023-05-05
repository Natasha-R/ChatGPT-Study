package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.ShipDeck;
import thkoeln.st.st2praktikum.repositories.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.repositories.ShipDeckRepository;

import java.util.UUID;

@Getter
@Service
public class MaintenanceDroidService {
    @Autowired
    private ShipDeckRepository shipDeckRepository;
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;


    
    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        maintenanceDroidRepository.save(maintenanceDroid);
        return maintenanceDroid.getId();
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

        if (order.getOrderType() == OrderType.ENTER) {
            if (spaceIsFree(order.getGridId())) {
                maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).encompass(order.getGridId());
                return true;
            } else {
                return false;
            }

        } else {
            MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId);
            if (maintenanceDroid==null){
                return false;
            }
            else
                return maintenanceDroid.preformOrder(order, shipDeckRepository.getShipDeckByid(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
        }
    }
    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        if (maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).getSpaceShipDeckId()!= null) {
            return maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).getSpaceShipDeckId();
        }
        else
            throw new UnsupportedOperationException();
    }

    /**
     * This method returns the coordinate a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinate of the maintenance droid
     */
    public Coordinate getMaintenanceDroidCoordinate(UUID maintenanceDroidId){
        if (maintenanceDroidId!=null) {
            return maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).getCoordinate();
        }
        else
            throw new UnsupportedOperationException();
    }

    public boolean spaceIsFree(UUID SpaceShipID ){
        for (MaintenanceDroid value : maintenanceDroidRepository.findAll()) {
            if (value.getPosx() == 0 && value.getPosy() == 0 && value.getSpaceShipDeckId().equals( SpaceShipID )) {
                return false;
            }
        }
        return true;
    }

    public Iterable<MaintenanceDroid> finAll(){
        return maintenanceDroidRepository.findAll();
    }
    public MaintenanceDroid updateRobots(MaintenanceDroid maintenanceDroid){
        return maintenanceDroidRepository.save(maintenanceDroid);
    }

    public MaintenanceDroid getMaintenanceDroid(UUID id){
        return maintenanceDroidRepository.getMaintenanceDroidByid(id);
    }

}
