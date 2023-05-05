package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.repositories.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.repositories.ShipDeckRepository;
import thkoeln.st.st2praktikum.repositories.TransportTechnologyRepository;

import java.util.Map;
import java.util.UUID;


@Service
public class MaintenanceDroidService {

    @Autowired
    private ShipDeckRepository shipDeckRepository;
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        ShipDeck shipDeck = new ShipDeck(height,width);
        shipDeckRepository.save(shipDeck);
        return shipDeck.getId();
    }

    /**
     * This method adds a wall to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        ShipDeck shipDeck = shipDeckRepository.getShipDeckByid(spaceShipDeckId);
        shipDeck.buildWall(wall);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getId();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId,UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        TransportTechnology transportTechnology = transportTechnologyRepository.getTransportTechnologyByid(transportTechnologyId);
        UUID connectionID = shipDeckRepository.getShipDeckByid(sourceSpaceShipDeckId).addConnection(transportTechnologyId,sourceSpaceShipDeckId,sourceCoordinate,destinationSpaceShipDeckId,destinationCoordinate);


        if (connectionID==null){
            throw new UnsupportedOperationException();
        }else{
        return connectionID;
        }

    }

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

        OrderType orderType = order.getOrderType();

        switch (orderType) {
            case TRANSPORT:
                return maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).traverse(order.getGridId(),
                        shipDeckRepository.getShipDeckByid(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
            case ENTER:
                if (spaceIsFree(order.getGridId())) {
                    maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).encompass(order.getGridId());
                    return true;
                }
                else
                    return false;
            case NORTH:
                maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).moveNorth(order.getNumberOfSteps(),
                        shipDeckRepository.getShipDeckByid(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case EAST:
                maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).moveEast(order.getNumberOfSteps(),
                        shipDeckRepository.getShipDeckByid(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case SOUTH:
                maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).moveSouth(order.getNumberOfSteps(),
                        shipDeckRepository.getShipDeckByid(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case WEST:
                maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).moveWest(order.getNumberOfSteps(),
                        shipDeckRepository.getShipDeckByid(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        if (maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).getNowInShipDeck()!= null) {
            return maintenanceDroidRepository.getMaintenanceDroidByid(maintenanceDroidId).getNowInShipDeck();
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
            if (value.posx == 0 && value.posy == 0 && value.getNowInShipDeck().equals( SpaceShipID )) {
                return false;
            }
        }
        return true;
    }
}
