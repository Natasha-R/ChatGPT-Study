package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.connection.ConnectionRepo;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.RoomRepo;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.TidyUpRobotRepo;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.Walkable;

import java.util.List;
import java.util.UUID;

public class TidyUpRobotService {
    private final TidyUpRobotRepo tidyUpRobotRepo;
    private final RoomRepo roomRepo;
    private final ConnectionRepo connectionRepo;

    public TidyUpRobotService() {
        this.tidyUpRobotRepo = new TidyUpRobotRepo();
        this.roomRepo = new RoomRepo();
        this.connectionRepo = new ConnectionRepo();
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(width, height);
        roomRepo.add(newRoom);
        return newRoom.getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        roomRepo.findById(roomId).addBarrier(barrier);
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
        Connection newConnection = new Connection(
                sourceRoomId,
                sourcePoint,
                destinationRoomId,
                destinationPoint
        );
        connectionRepo.add(newConnection);
        return newConnection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newTidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotRepo.add(newTidyUpRobot);
        return newTidyUpRobot.getId();
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
        TidyUpRobot tidyUpRobot = tidyUpRobotRepo.findById(tidyUpRobotId);

        switch (task.getTaskType()) {
            case ENTER:
                UUID targetRoomId = task.getGridId();
                List<Walkable> walkablesInTargetRoom = tidyUpRobotRepo.findByRoomIdAsWalkables(targetRoomId);
                return tidyUpRobot.placeInInitialRoom(targetRoomId, walkablesInTargetRoom);
            case TRANSPORT:
                List<Connection> connectionsInSourceRoom = connectionRepo.findByRoomId(tidyUpRobot.getRoomId());
                List<Walkable> walkablesInDestinationRoom = tidyUpRobotRepo.findByRoomIdAsWalkables(task.getGridId());
                return tidyUpRobot.transport(connectionsInSourceRoom, walkablesInDestinationRoom);
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                Room currentRoom = roomRepo.findById(tidyUpRobot.getRoomId());
                List<Walkable> walkablesInCurrentRoom = tidyUpRobotRepo.findByRoomIdAsWalkables(currentRoom.getId());
                tidyUpRobot.walk(task, currentRoom, walkablesInCurrentRoom);
                return true;
            default:
                throw new IllegalArgumentException(); //TODO: Create new exception for this.
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepo
                .findById(tidyUpRobotId)
                .getRoomId();
    }

    /**
     * This method returns the points a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the points of the tidy-up robot
     */
    public Point getPoint(UUID tidyUpRobotId){
        return tidyUpRobotRepo
                .findById(tidyUpRobotId)
                .getPoint();
    }
}
