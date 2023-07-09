package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.connection.ConnectionRepo;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.RoomRepo;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.TidyUpRobotRepo;

import java.util.List;
import java.util.StringTokenizer;
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
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID roomId, String barrierString) {
        roomRepo.findById(roomId).addBarrier(barrierString);
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
        Connection newConnection = new Connection(
                sourceRoomId,
                new Coordinate(sourceCoordinate),
                destinationRoomId,
                new Coordinate(destinationCoordinate)
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
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]".
     *                      The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepo.findById(tidyUpRobotId);

        StringTokenizer st = new StringTokenizer(commandString, "[],");
        String commandType = st.nextToken();
        String argument = st.nextToken();

        //Auswertung des commandStrings in dieser Methode wurde von Fabian abgesegnet.
        switch (commandType) {
            case "en":
                UUID targetRoomId = UUID.fromString(argument);
                List<Walkable> walkablesInTargetRoom = tidyUpRobotRepo.findByRoomIdAsWalkables(targetRoomId);
                return tidyUpRobot.placeInInitialRoom(targetRoomId, walkablesInTargetRoom);
            case "tr":
                List<Connection> connectionsInSourceRoom = connectionRepo.findByRoomId(tidyUpRobot.getRoomId());
                List<Walkable> walkablesInDestinationRoom = tidyUpRobotRepo.findByRoomIdAsWalkables(UUID.fromString(argument));
                return tidyUpRobot.transport(connectionsInSourceRoom, walkablesInDestinationRoom);
            case "no":
            case "ea":
            case "so":
            case "we":
                Room currentRoom = roomRepo.findById(tidyUpRobot.getRoomId());
                List<Walkable> walkablesInCurrentRoom = tidyUpRobotRepo.findByRoomIdAsWalkables(currentRoom.getId());
                tidyUpRobot.walk(commandString, currentRoom, walkablesInCurrentRoom);
                return true;
            default:
                throw new IllegalArgumentException();
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
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        return tidyUpRobotRepo
                .findById(tidyUpRobotId)
                .getCoordinate()
                .toString();
    }
}
