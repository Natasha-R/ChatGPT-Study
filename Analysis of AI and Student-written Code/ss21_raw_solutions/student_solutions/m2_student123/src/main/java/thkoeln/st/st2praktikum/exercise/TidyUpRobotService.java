package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobotService {

    private final HashMap<UUID,Room> roomHashMap = new HashMap<>();
    private final HashMap<UUID,Connection> connectionHashMap = new HashMap<>();
    private final HashMap<UUID,TidyUpRobot> tidyUpRobotHashMap = new HashMap<>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(width,height);
        this.roomHashMap.put(room.getRoomId(),room);
        return room.getRoomId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        if(roomHashMap.get(roomId) == null) throw new IllegalArgumentException("RoomId does not exist!");
        this.roomHashMap.get(roomId).setBorder(barrier);
    }


    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {
        Room source = roomHashMap.get(sourceRoomId);
        Room destination = roomHashMap.get(destinationRoomId);
        if (source == null || destination == null)
            throw new IllegalArgumentException("Source or Destination Room does not Exist: " + sourceRoomId + " or " + destinationRoomId);
        if(source.checkIfVectorOutOfBounds(sourceVector2D))
            throw new IllegalArgumentException("Coordinates are Out of Bounds: Source " + sourceVector2D.toString());
        if(destination.checkIfVectorOutOfBounds(destinationVector2D))
            throw new IllegalArgumentException("Coordinates are Out of Bounds: Destination " + destinationVector2D.toString());
        Connection connection = new Connection(sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);
        this.connectionHashMap.put(connection.getConnectionId(),connection);
        return connection.getConnectionId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot robot = new TidyUpRobot(name);
        this.tidyUpRobotHashMap.put(robot.getTidyUpRobotID(),robot);
        return robot.getTidyUpRobotID();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        if (this.tidyUpRobotHashMap.get(tidyUpRobotId) == null) {
            throw new IllegalArgumentException("This TidyUpRobot does not Exist.");
        }
        TidyUpRobot robot = this.tidyUpRobotHashMap.get(tidyUpRobotId);
        Room room = this.roomHashMap.get(robot.getRoomID());
        var success = false;
        switch (task.getTaskType()){
            case NORTH:
                success = robot.moveNorth(room, task.getNumberOfSteps());
                break;
            case SOUTH:
                success = robot.moveSouth(room,task.getNumberOfSteps());
                break;
            case EAST:
                success = robot.moveEast(room,task.getNumberOfSteps());
                break;
            case WEST:
                success = robot.moveWest(room,task.getNumberOfSteps());
                break;
            case TRANSPORT:
                success = robot.traverseRobot(this.connectionHashMap,task.getGridId());
                break;
            case ENTER:
                success = robot.placeRobot(this.roomHashMap,this.tidyUpRobotHashMap,task.getGridId());
                break;
            default:
                throw new UnsupportedOperationException("Fail");
        }
        return success;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return this.tidyUpRobotHashMap.get(tidyUpRobotId).getRoomID();
    }

    /**
     * This method returns the vector2Ds a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector2Ds of the tidy-up robot
     */
    public Vector2D getVector2D(UUID tidyUpRobotId){
        return tidyUpRobotHashMap.get(tidyUpRobotId).getCoordinates();
    }
}
