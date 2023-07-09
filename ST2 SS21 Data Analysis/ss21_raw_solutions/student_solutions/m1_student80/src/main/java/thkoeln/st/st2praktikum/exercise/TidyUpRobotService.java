package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class TidyUpRobotService {

    TidyUpRoboterList tidyUpRobotList = new TidyUpRoboterList();
    RoomList roomList = new RoomList();
    BarrierList barrierList = new BarrierList();
    ConnectionList connectionList = new ConnectionList();
    RoomRobotorHashMap roomRobotorHashMap = new RoomRobotorHashMap();
    CoordinatesForRobots coordinatesForRobots = new CoordinatesForRobots();


    /**
     * This method creates a new room.
     *
     * @param height the height of the room
     * @param width  the width of the room
     * @return the UUID of the created room
     */


    public UUID addRoom(Integer height, Integer width) {

        UUID roomUUID = UUID.randomUUID();
        Room newRoom = new Room(height, width, roomUUID);
        roomList.add(newRoom);
        String roomBarrierOne = new String("(" + 0 + "," + 0 + ")-(" + 0 + "," + (height));
        String roomBarrierTwo = new String("(" + 0 + "," + 0 + ")-(" + (width) + "," + 0);
        String roomBarrierThree = new String("(" + 0 + "," + (height) + ")-(" + (height) + "," + (width));
        String roomBarrierFour = new String("(" + (width) + "," + 0 + ")-(" + (width) + "," + (height));
        addBarrier(roomUUID, roomBarrierOne);
        addBarrier(roomUUID, roomBarrierTwo);
        addBarrier(roomUUID, roomBarrierThree);
        addBarrier(roomUUID, roomBarrierFour);
        return roomUUID;
    }

    /**
     * This method adds a barrier to a given room.
     *
     * @param roomId        the ID of the room the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID roomId, String barrierString) {

        Barrier newBarrier = new Barrier(barrierString);
        barrierList.add(newBarrier);
        for (Room room : roomList.getRoomList()) {
            if (roomId.equals(room.getRoomUUID())) {
                room.getBarrierList().add(newBarrier);
            }
        }
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     *
     * @param sourceRoomId          the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationRoomId     the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        UUID connectionUUID = UUID.randomUUID();
        Connection newConnection = new Connection(sourceRoomId, sourceCoordinate, destinationRoomId, destinationCoordinate, connectionUUID);
        connectionList.add(newConnection);
        return connectionUUID;
    }

    /**
     * This method adds a new tidy-up robot
     *
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        UUID tidyRoboterUUID = UUID.randomUUID();
        TidyUpRobot newTidyRobot = new TidyUpRobot(name, tidyRoboterUUID);
        tidyUpRobotList.add(newTidyRobot);
        return tidyRoboterUUID;
    }

    /**
     * This method lets the tidy-up robot execute a command.
     *
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     *                      "[direction, steps]" for movement, e.g. "[we,2]"
     *                      "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another room
     *                      "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     * another tidy-up robot, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        boolean canDoAction = true;
        for (TidyUpRobot robot : tidyUpRobotList.getTidyUpRobotsList()) {
            if (robot.getRoboterUUID() == tidyUpRobotId) {
                UUID roomWhereRobotIs = getTidyUpRobotRoomId(robot.getRoboterUUID());
                String moveCommand = commandString.substring(1, 3);
                String steps = commandString.substring(4, commandString.length() - 1);
                String UUID = commandString.substring(4, commandString.length() - 1);
                roomRobotorHashMap = robot.executeCommand(moveCommand, steps, UUID, tidyUpRobotList, roomList, connectionList, roomRobotorHashMap, roomWhereRobotIs, robot.getRoboterUUID(), coordinatesForRobots);
                canDoAction = robot.isCanDoAction();
            }
        }
        return canDoAction;
    }


    /**
     * This method returns the room-ID a tidy-up robot is standing on
     *
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId) {
        return roomRobotorHashMap.getRoomtidyUpRobotHashMap().get(tidyUpRobotId);
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     *
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId) {
        String coordinates = null;
        for (TidyUpRobot roboter : tidyUpRobotList.getTidyUpRobotsList()) {
            if (roboter.getRoboterUUID() == tidyUpRobotId) {
                coordinates = roboter.getStringCoordinates();
            }
        }
        return coordinates;
    }
}



