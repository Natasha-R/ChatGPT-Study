package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MaintenanceDroidService {

   private Space space = new Space();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     */

    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return space.createSpaceShipDeck(height, width);
    }


    /**
     * This method adds a barrier to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID spaceShipDeckId, String barrierString) {
        space.createBarrier(spaceShipDeckId, barrierString);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate,
                              UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        Integer xOfSource = Integer.parseInt(String.valueOf(sourceCoordinate.charAt(1)));
        Integer yOfSource = Integer.parseInt(String.valueOf(sourceCoordinate.charAt(3)));
        Integer xOfDestination = Integer.parseInt(String.valueOf(destinationCoordinate.charAt(1)));
        Integer yOfDestination = Integer.parseInt(String.valueOf(destinationCoordinate.charAt(3)));

        return space.createConnection(sourceSpaceShipDeckId,new Point(xOfSource,yOfSource),destinationSpaceShipDeckId,new Point(xOfDestination,yOfDestination));
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return space.createMaintenanceDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        return space.maintenanceDroids.get(maintenanceDroidId).executeCommand(maintenanceDroidId, commandString);
    }



    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return space.getMDSpaceShipDeckId(maintenanceDroidId);
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        String stringOfCordinates = "("+ space.maintenanceDroids.get(maintenanceDroidId).coordinate.getX() +
                ","+ space.maintenanceDroids.get(maintenanceDroidId).coordinate.getY()+")";
        return stringOfCordinates;
    }
}
