package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class TidyUpRobotService {

    /**
     * This method creates a new room...
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height,width);
        RoomMap.addToMap(room,room.getRoomId());
        return room.getRoomId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        wall.placeWallInRoom(roomId);
        WallMap.addToMap(wall, wall.getWallId());
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        TraversableConnection traversableConnection = new TraversableConnection(sourceRoomId,sourcePoint,destinationRoomId,destinationPoint);
        TraversableConnectionMap.addToMap(traversableConnection,traversableConnection.getSourceRoomId());
        return traversableConnection.getConnectionId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
       TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
       TidyUpRobotMap.addToMap(tidyUpRobot,tidyUpRobot.getTidyUpRobotId());
       return tidyUpRobot.getTidyUpRobotId();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        if(command.getCommandType().equals(CommandType.TRANSPORT)){
            Boolean connectionIsThere = TraversableConnectionMap.findConnectionOnPoint(TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentPosition(), TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentRoomId());
            if(connectionIsThere){
                TidyUpRobotMap.getByUUID(tidyUpRobotId).travel(TraversableConnectionMap.findConnectionInRoom(TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentRoomId()).getDestinationRoomId(),TraversableConnectionMap.findConnectionInRoom(TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentRoomId()).getDestinationPoint().getCurrentPosition());
                return true;
            }
            else return false;
        }
        if(command.isMoveCommand(command.getCommandType())){
            boolean updown = command.getIsHorizontalMovement();
            TidyUpRobotMap.getByUUID(tidyUpRobotId).moveRobot(command, WallMap.findAllRelevantWallsInRoom(TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentRoomId(), !updown), RoomMap.getByUUID(TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentRoomId()).getHeight()-1,RoomMap.getByUUID(TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentRoomId()).getWidth()-1);
            return true;
        }
        else {
            Boolean spawnPointBlocked = TidyUpRobotMap.unMovedRobot(command.getGridId());
            return TidyUpRobotMap.getByUUID(tidyUpRobotId).spawnRobot(command.getGridId(), spawnPointBlocked);
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return TidyUpRobotMap.getByUUID(tidyUpRobotId).getCurrentRoomId();
    }

    /**
     * This method returns the points a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the points of the tidy-up robot
     */
    public Point getPoint(UUID tidyUpRobotId){
        return TidyUpRobotMap.getByUUID(tidyUpRobotId).curentPointOfRobot();
    }
}
