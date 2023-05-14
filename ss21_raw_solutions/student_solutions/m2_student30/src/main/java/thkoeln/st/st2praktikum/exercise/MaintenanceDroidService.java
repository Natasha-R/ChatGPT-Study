package thkoeln.st.st2praktikum.exercise;

import java.util.Map;
import java.util.UUID;

public class MaintenanceDroidService {
    private SpaceShip spaceShip = new SpaceShip();
    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        ShipDeck shipDeck = new ShipDeck(height,width);
        UUID id = UUID.randomUUID();
        spaceShip.getShipDeckHashMap().put(id,shipDeck);
        return id;
    }

    /**
     * This method adds a wall to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        ShipDeck shipDeck = spaceShip.getShipDeckHashMap().get(spaceShipDeckId);
        shipDeck.buildWall(wall);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        UUID connectionID = UUID.randomUUID();

        if(sourceCoordinate.getX() > spaceShip.getShipDeckHashMap().get(sourceSpaceShipDeckId).getWidth() ||
                sourceCoordinate.getY() > spaceShip.getShipDeckHashMap().get(sourceSpaceShipDeckId).getHeight() ||
                destinationCoordinate.getX() > spaceShip.getShipDeckHashMap().get(destinationSpaceShipDeckId).getWidth() ||
                destinationCoordinate.getX() > spaceShip.getShipDeckHashMap().get(destinationSpaceShipDeckId).getHeight()){

            throw new UnsupportedOperationException("Diese Connection ist out of bound gesetzt");
        }else{

        spaceShip.getShipDeckHashMap().get(sourceSpaceShipDeckId).getConnectionHashMap().
                put(sourceSpaceShipDeckId, new Connection(sourceSpaceShipDeckId,sourceCoordinate,destinationSpaceShipDeckId,destinationCoordinate));
        return connectionID;
        }
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintanceDroid MaintanceDroid = new MaintanceDroid(name);
        UUID id = UUID.randomUUID();
        spaceShip.getDroidHashMap().put(id,MaintanceDroid);
        return id;
    }

    /**
     * This method lets the maintenance droid execute a order.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another spaceShipDeck
     * ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
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
                return spaceShip.getDroidHashMap().get(maintenanceDroidId).traverse(order.getGridId(),
                        spaceShip.getShipDeckHashMap().get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
            case ENTER:
                if (spaceIsFree(order.getGridId())) {
                    spaceShip.getDroidHashMap().get(maintenanceDroidId).encompass(order.getGridId());
                    return true;
                }
                else
                    return false;
            case NORTH:
                spaceShip.getDroidHashMap().get(maintenanceDroidId).moveNorth(order.getNumberOfSteps(),
                        spaceShip.getShipDeckHashMap().get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case EAST:
                spaceShip.getDroidHashMap().get(maintenanceDroidId).moveEast(order.getNumberOfSteps(),
                        spaceShip.getShipDeckHashMap().get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case SOUTH:
                spaceShip.getDroidHashMap().get(maintenanceDroidId).moveSouth(order.getNumberOfSteps(),
                        spaceShip.getShipDeckHashMap().get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case WEST:
                spaceShip.getDroidHashMap().get(maintenanceDroidId).moveWest(order.getNumberOfSteps(),
                        spaceShip.getShipDeckHashMap().get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        if (spaceShip.getDroidHashMap().get(maintenanceDroidId).getNowInShipDeck()!= null) {
            return spaceShip.getDroidHashMap().get(maintenanceDroidId).getNowInShipDeck();
        }
        else
            throw new UnsupportedOperationException();
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinates of the maintenance droid
     */
    public Coordinate getCoordinate(UUID maintenanceDroidId){
        if (maintenanceDroidId!=null) {
            return spaceShip.getDroidHashMap().get(maintenanceDroidId).getCoordinates();
        }
        else
            throw new UnsupportedOperationException();
    }

    public boolean spaceIsFree(UUID SpaceShipID ){
        for (Map.Entry<UUID, MaintanceDroid> entry : spaceShip.getDroidHashMap().entrySet()) {
            MaintanceDroid value = entry.getValue();
            if (value.posx == 0 && value.posy == 0 && value.getNowInShipDeck().equals( SpaceShipID )) {
                return false;
            }
        }
        return true;
    }
}
