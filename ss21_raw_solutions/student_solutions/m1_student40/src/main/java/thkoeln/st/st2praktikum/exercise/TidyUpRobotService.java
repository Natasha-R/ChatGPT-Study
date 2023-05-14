package thkoeln.st.st2praktikum.exercise;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TidyUpRobotService {

    private List<Room> rooms = new ArrayList<Room>();
    private List<Connection> connections = new ArrayList<Connection>();
    private List<Robot> robots = new ArrayList<Robot>();

    private Room getRoomByID(UUID roomID) {
        for (Room room : this.rooms) {
            if (room.getId().equals(roomID)) {
                return room;
            }
        }

        throw new UnsupportedOperationException("Invalid roomID " + roomID);
    }

    private Robot getRobotByID(UUID robotID) {
        for (Robot robot : this.robots) {
            if (robot.getId().equals(robotID))
                return robot;
        }

        throw new UnsupportedOperationException("Invalid robotID");
    }

    private boolean placeNewRobot(UUID robotID, String roomIDString){
        UUID roomID = UUID.fromString(roomIDString);

        if( !getRoomByID(roomID).getTile(0,0).placeRobotOnTile(robotID) )
            return false;

        return getRobotByID(robotID).placeInRoom(roomID);
    }

    private boolean travelConnection(UUID robotID){
        if( !getRobotByID(robotID).isInRoom() )
            return false;

        Tile currentTile = getRoomByID(getRobotByID(robotID).getCurrentRoomID()).getTile(getRobotByID(robotID).position.getX(), getRobotByID(robotID).position.getY());

        if(!currentTile.hasConnection())
            return false;

        for( Connection currentConnection : currentTile.getConnections() ){
            if(!currentConnection.canUse(getRobotByID(robotID).getCurrentRoomID()))
                continue;

            Coordinate connectionDestinationLocation = getRoomByID(currentConnection.getDestinationRoomID()).findConnectionLocation(currentConnection.getId());
            Tile destinationTile = getRoomByID(currentConnection.getDestinationRoomID()).getTile(connectionDestinationLocation.getX(), connectionDestinationLocation.getY());

            if( !destinationTile.placeRobotOnTile(getRobotByID(robotID).getId()) )
                continue;

            currentTile.removeRobot();

            return getRobotByID(robotID).switchRoom(currentConnection.getDestinationRoomID(), connectionDestinationLocation);
        }

        return false;
    }

    private void moveRobot(UUID robotID, String moveTypeString, String moveAmountString){
        moveType currentMoveType = CustomHelpers.getMoveTypeFromString(moveTypeString);
        int amountToMove = getRoomByID(getRobotByID(robotID).getCurrentRoomID()).getMaxMoveAmount(getRobotByID(robotID).position.getX(), getRobotByID(robotID).position.getY(), currentMoveType, Integer.parseInt(moveAmountString), robotID);

        if( amountToMove > 0 ) {
            Tile currentTile = getRoomByID(getRobotByID(robotID).getCurrentRoomID()).getTile(getRobotByID(robotID).position.getX(), getRobotByID(robotID).position.getY());
            currentTile.removeRobot();

            getRobotByID(robotID).move(currentMoveType, amountToMove);

            Tile newTile = getRoomByID(getRobotByID(robotID).getCurrentRoomID()).getTile(getRobotByID(robotID).position.getX(), getRobotByID(robotID).position.getY());
            newTile.placeRobotOnTile(robotID);
        }
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {

        Room room = new Room(width, height);
        this.rooms.add(room);

        return room.getId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID roomId, String wallString) {
        getRoomByID(roomId).addWall(wallString);
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        Connection newConnection = new Connection(sourceRoomId, destinationRoomId);
        this.connections.add(newConnection);

        getRoomByID(sourceRoomId).addConnection(newConnection, sourceCoordinate);
        getRoomByID(destinationRoomId).addConnection(newConnection, destinationCoordinate);

        return newConnection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        Robot newRobot = new Robot(name);
        this.robots.add(newRobot);

        return newRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        if( commandString.isEmpty() )
            return false;

        commandString = commandString.replace("[", "");
        commandString = commandString.replace("]", "");
        String[] explodedCommand = commandString.split(",");

        switch (explodedCommand[0]){
            case "en":{
                return placeNewRobot(tidyUpRobotId, explodedCommand[1]);
            }
            case "tr":{
                return travelConnection(tidyUpRobotId);
            }
            case "we":
            case "ea":
            case "so":
            case "no":{
                moveRobot(tidyUpRobotId, explodedCommand[0], explodedCommand[1]);
                break;
            }
        }

        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return getRobotByID(tidyUpRobotId).getCurrentRoomID();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){ return getRobotByID(tidyUpRobotId).position.toString(); }
}
