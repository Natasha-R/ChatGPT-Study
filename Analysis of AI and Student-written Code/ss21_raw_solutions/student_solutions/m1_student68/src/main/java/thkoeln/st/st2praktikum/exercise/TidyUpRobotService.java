package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.core.RobotCommand;
import thkoeln.st.st2praktikum.exercise.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.barrier.Blockable;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.map.Map;
import thkoeln.st.st2praktikum.exercise.map.Operable;
import thkoeln.st.st2praktikum.exercise.robot.Instructable;
import thkoeln.st.st2praktikum.exercise.robot.Robot;
import thkoeln.st.st2praktikum.exercise.room.Buildable;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.Walkable;

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
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID roomId, String barrierString) {
        Blockable barrier = new Barrier(barrierString);
        map.getRoomById(roomId).addBarrier(barrier);
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
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        RobotCommand robotCommand = new RobotCommand(commandString, map);
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
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        return map.getRobotById(tidyUpRobotId).getPosition().toString();
    }
}
