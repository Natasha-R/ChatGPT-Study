package thkoeln.st.st2praktikum.exercise.control;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.map.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.map.Locatable;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class Controller {

    Environment environment = new Environment();


    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return environment.addGrid(height, width);
    }

    /**
     * This method adds a barrier to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        environment.getGrids().get(spaceShipDeckId).addBarriers( barrier );
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return environment.addDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        return null;
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return environment.getDroids().get(maintenanceDroidId).getPosition().getSpaceShipDeckID();
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public Locatable getPoint(UUID maintenanceDroidId){
        return environment.getDroids().get( maintenanceDroidId ).getPosition();
    }

    public HashMap<String, Locatable> getNodes( UUID gridID ){
        return environment.getGrids().get( gridID ).getPoints();
    }

    public MaintenanceDroid getDroidByUUID(UUID droidID){
        try {
            return getEnvironment().getDroids().get(droidID);
        } catch (NullPointerException e){
            System.out.println("MaintenanceDroid: " + droidID + " is not in this list");
            return new MaintenanceDroid("nameless");
        }
    }

    public SpaceShipDeck getGridByID(UUID deckID){
        return getEnvironment().getGrids().get(deckID);
    }










}
