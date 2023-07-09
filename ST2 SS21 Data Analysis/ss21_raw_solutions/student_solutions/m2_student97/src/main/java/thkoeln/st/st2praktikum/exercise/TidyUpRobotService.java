package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class TidyUpRobotService {

    Environment environment = new Environment();
    Controller controller = new Controller(environment);

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        return environment.addRoom(height, width);
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        environment.getRoom(roomId).addBarrier(barrier);
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
        return environment.getRoom(sourceRoomId).addConnection(sourcePoint, destinationRoomId, destinationPoint);
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        return environment.addRobot(name);
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        return controller.processCommand(tidyUpRobotId, command);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return environment.getRobot(tidyUpRobotId).getRoom().getId();
    }

    /**
     * This method returns the points a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the points of the tidy-up robot
     */
    public Point getPoint(UUID tidyUpRobotId){
        return environment.getRobot(tidyUpRobotId).getPosition();
    }
}
