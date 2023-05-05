package thkoeln.st.st2praktikum.exercise.universe;

import thkoeln.st.st2praktikum.exercise.command.CommandInterface;
import thkoeln.st.st2praktikum.exercise.command.Direction;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.exception.MaintenanceDroidPositionIsNull;
import thkoeln.st.st2praktikum.exercise.exception.SquareNotFoundException;
import thkoeln.st.st2praktikum.exercise.maintenanceDroid.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenanceDroid.MaintenanceDroidInterface;
import thkoeln.st.st2praktikum.exercise.maintenanceDroid.MaintenanceDroidPosition;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstacleInterface;
import thkoeln.st.st2praktikum.exercise.services.StringService;
import thkoeln.st.st2praktikum.exercise.services.UniverseService;
import thkoeln.st.st2praktikum.exercise.spaceShipDeck.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceShipDeck.Square;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Universe implements UniverseInterface {

    private HashMap<UUID, SpaceShipDeck> spaceShipDeckHashMap = new HashMap<>();
    private HashMap<UUID, MaintenanceDroid> maintenanceDroidHashMap = new HashMap<>();


    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck newSpaceShipDeck = new SpaceShipDeck(height, width);
        spaceShipDeckHashMap.put(newSpaceShipDeck.getSpaceShipDeckID(),newSpaceShipDeck);
        return newSpaceShipDeck.getSpaceShipDeckID();
    }

    /**
     * This method adds a obstacle to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceShipDeckId, String obstacleString) {


        ObstacleInterface resultObstacle = StringService.translateToObstacle(obstacleString);
        if (resultObstacle.getStartX().equals(resultObstacle.getEndX())) {
            for (int i = resultObstacle.getStartY(); i < resultObstacle.getEndY(); i++) {
                spaceShipDeckHashMap.get(spaceShipDeckId).getAllObstacles().add(new Obstacle(new Coordinate(resultObstacle.getStartX(), i), new Coordinate(resultObstacle.getEndX(), i + 1)));
            }
        }
        if (resultObstacle.getStartY().equals(resultObstacle.getEndY())) {
            for (int i = resultObstacle.getStartX(); i < resultObstacle.getEndX(); i++) {
                spaceShipDeckHashMap.get(spaceShipDeckId).getAllObstacles().add(new Obstacle(new Coordinate(i, resultObstacle.getStartY()), new Coordinate(i + 1, resultObstacle.getEndY())));
            }
        }

    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        Connection connection = new Connection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
        try {
            spaceShipDeckHashMap.get(sourceSpaceShipDeckId).findSquareAt(StringService.translateToCoordinate(sourceCoordinate)).setConnection(connection);
            return connection.getConnectionID();
        } catch (SquareNotFoundException e) {
//            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        maintenanceDroidHashMap.put(maintenanceDroid.getMaintenanceDroidID(), maintenanceDroid);
        return maintenanceDroid.getMaintenanceDroidID();
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        CommandInterface command = StringService.translateToCommandPair(commandString);
        MaintenanceDroidInterface maintenanceDroid = maintenanceDroidHashMap.get(maintenanceDroidId);
        try{
            if(command.isStepCommand()){
                return UniverseService.isStepCommand(command, maintenanceDroid, this);
            }
            if(command.isUUIDCommand()){
                return UniverseService.isUUIDCommand(command,maintenanceDroid,this);
            }
        }catch (MaintenanceDroidPositionIsNull | IllegalStateException | SquareNotFoundException e) {
//            System.out.println(e.toString());
            return false;
        };
//        System.out.println("executeCommand Failure");
        return false;
    }
    public Boolean isTransportOccupied(UUID squareID, UUID maintenancesDroidID) {
        AtomicReference<Boolean> result = new AtomicReference<>();
        result.set(false);
        maintenanceDroidHashMap.forEach((key, value)-> {
            if(key != maintenancesDroidID){
                try {
                    if(value.getMaintenanceDroidPosition().getSquareID() == squareID) result.set(true);
                } catch (MaintenanceDroidPositionIsNull maintenanceDroidPositionIsNull) {
//                    System.out.println(maintenanceDroidPositionIsNull.toString());;
                }
            }
        });
        return result.get();
    }

    public Boolean isSquareOccupied(Direction direction, UUID squareID, UUID maintenancesDroidID, SpaceShipDeck spaceShipDeck) throws SquareNotFoundException {
        AtomicReference<Boolean> result = new AtomicReference<>();
        result.set(false);
        UUID nextSquareID = spaceShipDeck.getNextSquareUUIDinDirection(direction, squareID);
        maintenanceDroidHashMap.forEach((uuid, maintenanceDroid)-> {
            if(uuid != maintenancesDroidID){
                try {
                    if(maintenanceDroid.getMaintenanceDroidPosition().getSquareID() == nextSquareID) result.set(true);
                } catch (MaintenanceDroidPositionIsNull maintenanceDroidPositionIsNull) {
//                    System.out.println(maintenanceDroidPositionIsNull.toString());
                }
            }
        });
        return result.get();
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        try {
            return maintenanceDroidHashMap.get(maintenanceDroidId).getMaintenanceDroidPosition().getSpaceShipDeckID();
        }
        catch (MaintenanceDroidPositionIsNull e){
//            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public HashMap<UUID, SpaceShipDeck> getSpaceShipDeckHashMap() {
        return this.spaceShipDeckHashMap;
    }

    @Override
    public HashMap<UUID, MaintenanceDroid> getMaintenanceDroidHashMap() {
        return this.maintenanceDroidHashMap;
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        try {
            MaintenanceDroidPosition tmpMaintenanceDroidPosition = maintenanceDroidHashMap.get(maintenanceDroidId).getMaintenanceDroidPosition();
            Square tmpSquare = spaceShipDeckHashMap.get(tmpMaintenanceDroidPosition.getSpaceShipDeckID()).getSquareHashMap().get(tmpMaintenanceDroidPosition.getSquareID());
            return "("+tmpSquare.getCoordinates().getX()+","+tmpSquare.getCoordinates().getY()+")";
        }
        catch (MaintenanceDroidPositionIsNull e){
//            System.out.println(e.toString());
            return null;
        }
    }
}
