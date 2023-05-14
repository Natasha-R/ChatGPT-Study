package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MaintenanceDroidService {
    UUID uUidShipDeck;
    int xPos;
    int yPos;
    boolean firstTime = true;

    /**
     * This method creates a new spaceShipDeck.
     *
     * @param height the height of the spaceShipDeck
     * @param width  the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        UUID spaceShipDeckUUID = UUID.randomUUID();
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height, width, spaceShipDeckUUID);
        return spaceShipDeckUUID;
    }

    /**
     * This method adds a barrier to a given spaceShipDeck.
     *
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrierString   the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID spaceShipDeckId, String barrierString) {
        Barrier barrier = new Barrier(spaceShipDeckId, barrierString);

    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     *
     * @param sourceSpaceShipDeckId      the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate           the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate      the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        UUID connectionUUID = UUID.randomUUID();
        Connection connection = new Connection(connectionUUID, sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
        return connectionUUID;
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        UUID maintenanceDroidUUID = UUID.randomUUID();
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(maintenanceDroidUUID, name);
        return maintenanceDroidUUID;
    }

    /**
     * This method lets the maintenance droid execute a command.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString      the given command, encoded as a String:
     *                           "[direction, steps]" for movement, e.g. "[we,2]"
     *                           "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" -
     *                           only works on tiles with a connection to another spaceShipDeck
     *                           "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed,
     *                           e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a barrier or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {

        MaintenanceDroid maintenanceDroid = MaintenanceDroid.getMaintenanceDroid(maintenanceDroidId);
        String specifyCommand = commandString.split(",")[0].toString().substring(1, 3);

        if (specifyCommand.equals("tr")) {
            uUidShipDeck = UUID.fromString(commandString.split(",")[1].toString().substring(0, commandString.split(",")[1].length() - 1));

            return transportToDestinationDeck(maintenanceDroid);

        } else if (specifyCommand.equals("en")) {
            uUidShipDeck = UUID.fromString(commandString.split(",")[1].toString().substring(0, commandString.split(",")[1].length() - 1));

            return initializeDroid(maintenanceDroid, uUidShipDeck, maintenanceDroidId);

        } else {

            move(maintenanceDroid, specifyCommand, commandString);
            return true;
        }
    }

    private boolean initializeDroid(MaintenanceDroid maintenanceDroid, UUID uUid, UUID maintenanceDroidId) {
        /*if (SpaceShipDeck.getSpaceShipDeck(uUid).getMaintenanceDroidID() != null) {
            if (MaintenanceDroid.getMaintenanceDroid(SpaceShipDeck.getSpaceShipDeck(uUid).getMaintenanceDroidID()) != null) {
                if (MaintenanceDroid.getMaintenanceDroid(SpaceShipDeck.getSpaceShipDeck(uUid).getMaintenanceDroidID()).getXPosition() == 0
                        && MaintenanceDroid.getMaintenanceDroid(SpaceShipDeck.getSpaceShipDeck(uUid).getMaintenanceDroidID()).getYPosition() == 0) {
                    return false;
                }
            }
        }*/

        maintenanceDroid.setMaintenanceDroidSpaceShipDeckId(uUid);
        SpaceShipDeck.setMaintenanceDroidID(maintenanceDroidId);
        maintenanceDroid.setXPosition(0);
        maintenanceDroid.setYPosition(0);
        return true;
    }

    private boolean transportToDestinationDeck(MaintenanceDroid maintenanceDroid) {
            maintenanceDroid.setMaintenanceDroidSpaceShipDeckId(Connection.getConnection(uUidShipDeck).getSourceSpaceShipDeckId());
            int targetX = Integer.parseInt(Connection.getConnection(uUidShipDeck).getSourceCoordinate().split(",")[0].substring(1));
            int targetY = Integer.parseInt(Connection.getConnection(uUidShipDeck).getSourceCoordinate().split(",")[1].substring(0, Connection.getConnection(uUidShipDeck).getDestinationCoordinate().split(",")[1].length() - 1));

            maintenanceDroid.setXPosition(targetX);
            maintenanceDroid.setYPosition(targetY);

            return true;
    }

    private void move(MaintenanceDroid maintenanceDroid, String specifyCommand, String commandString) {

        if(maintenanceDroid.getXPosition() == 0 && maintenanceDroid.getYPosition() == 0){
            xPos = maintenanceDroid.getXPosition();
            yPos = maintenanceDroid.getYPosition();
            firstTime = false;
        }


        int lowerBoundary = 0;
        int upperBoundary = SpaceShipDeck.getSpaceShipDeck(uUidShipDeck).getHeight();
        int leftBoundary = 0;
        int rightBoundary = SpaceShipDeck.getSpaceShipDeck(uUidShipDeck).getWidth();

        int stepSize = Integer.parseInt(commandString.split(",")[1].substring(0, 1));

        int barrierX_0 = -1;
        int barrierY_0 = -1;
        int barrierX_1 = -1;
        int barrierY_1 = -1;

        if(Barrier.getBarrier(uUidShipDeck) != null){
            barrierX_0 = Integer.parseInt(Barrier.getBarrier(uUidShipDeck).getBarrierString().split("-")[0].split(",")[0].substring(1));
            barrierY_0 = Integer.parseInt(Barrier.getBarrier(uUidShipDeck).getBarrierString().split("-")[0].split(",")[1].substring(0, Barrier.getBarrier(uUidShipDeck).getBarrierString().split("-")[0].split(",")[1].length() - 1));

            barrierX_1 = Integer.parseInt(Barrier.getBarrier(uUidShipDeck).getBarrierString().split("-")[1].split(",")[0].substring(1));
            barrierY_1 = Integer.parseInt(Barrier.getBarrier(uUidShipDeck).getBarrierString().split("-")[1].split(",")[1].substring(0, Barrier.getBarrier(uUidShipDeck).getBarrierString().split("-")[0].split(",")[1].length() - 1));

        }

        switch (specifyCommand) {

            case "no": {
                for (int i = 0; i < stepSize; i++) {
                    yPos++;
                    if (yPos == upperBoundary) {
                        break;
                    }
                    if (yPos > upperBoundary) {
                        yPos--;
                        break;
                    }
                    //obstacle
                    if (barrierX_0 >= 0 && barrierY_0 == barrierY_1) {
                        if (yPos == barrierY_0 && (xPos >= barrierX_0 && xPos <= barrierX_1)) {
                            yPos--;
                            break;
                        }
                    }
                    maintenanceDroid.setYPosition(yPos);
                    maintenanceDroid.setXPosition(xPos);
                }
                break;
            }
            case "so": {
                for (int i = 0; i < stepSize; i++) {
                    yPos--;
                    if (yPos == lowerBoundary) {
                        break;
                    }
                    if (yPos < lowerBoundary) {
                        yPos++;
                        break;
                    }
                    //obstacle
                    if (barrierX_0 >= 0 && barrierY_0 == barrierY_1) {
                        if (yPos == barrierY_0 && (xPos >= barrierX_0 && xPos <= barrierX_1)) {
                            break;
                        }
                    }
                    maintenanceDroid.setYPosition(yPos);
                    maintenanceDroid.setXPosition(xPos);
                }
                break;
            }

            case "ea": {
                for (int i = 0; i < stepSize; i++) {
                    xPos++;
                    if (xPos >= rightBoundary) {
                        xPos--;
                        break;
                    }
                    //obstacle
                    if (barrierX_0 >= 0 && barrierX_0 == barrierX_1) {
                        if (xPos == barrierX_0 && (yPos >= barrierY_0 && yPos <= barrierY_1)) {
                            xPos--;
                            break;
                        }
                    }
                    maintenanceDroid.setXPosition(xPos);
                    maintenanceDroid.setYPosition(yPos);
                }
                break;
            }
            case "we": {
                for (int i = 0; i < stepSize; i++) {
                    xPos--;
                    if (xPos == leftBoundary) {
                        break;
                    }
                    if (xPos < leftBoundary) {
                        xPos++;
                        break;
                    }
                    //obstacle
                    if (barrierX_0 >= 0 && barrierX_0 == barrierX_1) {
                        if (xPos == barrierX_0 && (yPos >= barrierY_0 && yPos <= barrierY_1)) {
                            break;
                        }
                    }
                    maintenanceDroid.setXPosition(xPos);
                    maintenanceDroid.setYPosition(yPos);
                }
                break;
            }
            default:
                System.out.println("Invalid command");
        }
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        return MaintenanceDroid.getMaintenanceDroid(maintenanceDroidId).getMaintenanceDroidSpaceShipDeckId();
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId) {
        return MaintenanceDroid.getMaintenanceDroid(maintenanceDroidId).getPosition();
    }
}
