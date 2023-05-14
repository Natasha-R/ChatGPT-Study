package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.core.RobotCommand;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.obstacle.Blocking;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.robot.Instructable;
import thkoeln.st.st2praktikum.exercise.robot.Robot;
import thkoeln.st.st2praktikum.exercise.room.Buildable;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.Walkable;
import thkoeln.st.st2praktikum.exercise.map.Map;
import thkoeln.st.st2praktikum.exercise.map.Operable;

import java.util.UUID;

public class TidyUpRobotService {
    public final Operable map = new Map();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Buildable room = new Room(height,width);
        map.addRoom(room);
        return room.getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        Blocking obstacle = new Obstacle(barrier);
        map.getRoomById(roomId).addObstacle(obstacle);
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        Connectable connection = new Connection( (Walkable) map.getRoomById(sourceRoomId) ,sourceCoordinate, (Walkable) map.getRoomById(destinationRoomId), destinationCoordinate);
        return map.getRoomById(sourceRoomId).addConnection(connection);
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        Instructable robot = new Robot(name);
        map.addRobot(robot);
        return robot.getId();
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
        RobotCommand robotCommand = new RobotCommand(task, map);
        return map.getRobotById(tidyUpRobotId).readCommand(robotCommand);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return map.getRobotById(tidyUpRobotId).getRoom().getId();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId){
         return map.getRobotById(tidyUpRobotId).getCoordinate();
    }
}
