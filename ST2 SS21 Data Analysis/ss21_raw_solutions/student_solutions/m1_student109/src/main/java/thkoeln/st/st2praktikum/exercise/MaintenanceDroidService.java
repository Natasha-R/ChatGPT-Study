package thkoeln.st.st2praktikum.exercise;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MaintenanceDroidService {

   private List<SpaceShipDeck> spaceShipDecks = new ArrayList<>();
   private List<MaintenanceDroid> maintenanceDroids = new ArrayList<>();


    /**
     * This method creates a new spaceShipDeck.
     * 
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        UUID spaceShipDeskId =  UUID.randomUUID();
       spaceShipDecks.add(new SpaceShipDeck(height, width, spaceShipDeskId));
       return spaceShipDeskId;
    }

    /**
     * This method adds a obstacle to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceShipDeckId, String obstacleString) {
            for (int i = 0; i < spaceShipDecks.size(); i ++){
                if (spaceShipDecks.get(i).getSpaceShipDeskId().equals(spaceShipDeckId)){
                    spaceShipDecks.get(i).addObstacle(obstacleString);
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
        UUID connectionId = UUID.randomUUID();
        for (SpaceShipDeck spaceShipDeck: spaceShipDecks){
            if (spaceShipDeck.getSpaceShipDeskId().equals(sourceSpaceShipDeckId)){
                spaceShipDeck.addConnection(new Connection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate, connectionId));
            }
        }
        return connectionId;
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        UUID maintenanceDroidsId = UUID.randomUUID();
        maintenanceDroids.add(new MaintenanceDroid(name, maintenanceDroidsId));
        return maintenanceDroidsId;
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
    public boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        String commands[] = commandString.split(",");
        String command = commands[0].replace("[", " ").trim();
        String step= commands[1].replace("]", " ").trim();

            switch (command){
                case "no":
                case "ea":
                case "we":
                case "so": return move(maintenanceDroidId, command, step);
                case "tr": return transport(maintenanceDroidId, step);
                case "en": return entry(maintenanceDroidId, step);
                default: return false;
            }
        }

    private boolean move(UUID maintenanceDroidId, String command, String step ) {
            for (MaintenanceDroid maintenanceDroid: maintenanceDroids){
                if (maintenanceDroid.getMaintenanceDroidsId().equals(maintenanceDroidId)){
                    for (SpaceShipDeck spaceShipDeck: spaceShipDecks){
                        if (spaceShipDeck.getSpaceShipDeskId().equals(maintenanceDroid.getSpaceShipDeskId())){
                            spaceShipDeck.moveMaintenanceDroid(maintenanceDroid, command, step, maintenanceDroids);
                            return true;
                        }
                    }
                }
            }
            return false;
    }

    private boolean entry(UUID maintenanceDroidId, String step) {
        for (SpaceShipDeck spaceShipDeck : spaceShipDecks) {
            if (UUID.fromString(step).equals(spaceShipDeck.getSpaceShipDeskId())) {
                for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
                    if (maintenanceDroid.getMaintenanceDroidsId().equals(maintenanceDroidId)) {
                        if (spaceShipDeck.checkPositionForDroid(maintenanceDroids,0,0) == true) {
                            maintenanceDroid.setSpaceShipDeskId(UUID.fromString(step));
                            maintenanceDroid.setMaintenceDroidsYPosition(0);
                            maintenanceDroid.setMaintenceDroidsXPosition(0);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean transport(UUID maintenanceDroidId, String step) {
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids){
            if (maintenanceDroid.getMaintenanceDroidsId().equals(maintenanceDroidId)){
                for (SpaceShipDeck spaceShipDeck: spaceShipDecks){
                    if (maintenanceDroid.getSpaceShipDeskId().equals(spaceShipDeck.getSpaceShipDeskId())){
                        MaintenanceDroid tmp = spaceShipDeck.transport(maintenanceDroid, step, maintenanceDroids);
                        if (maintenanceDroid.getSpaceShipDeskId() == tmp.getMaintenanceDroidsId()){
                            maintenanceDroid = tmp;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        for (int i = 0; i < maintenanceDroids.size(); i++){
            if (maintenanceDroids.get(i).getMaintenanceDroidsId().equals(maintenanceDroidId)){
                return maintenanceDroids.get(i).getSpaceShipDeskId();
            }
        }
        return null;
    }
    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        for (int i = 0; i < maintenanceDroids.size(); i++){
            if (maintenanceDroids.get(i).getMaintenanceDroidsId().equals(maintenanceDroidId)){
                return maintenanceDroids.get(i).getMaintenceDroidsPosition();
            }
        }
        return null;
    }
}
