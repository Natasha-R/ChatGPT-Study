package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public class TidyUpRobotService {


    TidyUpRobotRepository tidyUpRobotRepository = new TidyUpRobotRepository();
    RoomRepository roomRepository = new RoomRepository();
    ConnectionRepository connectionRepository = new ConnectionRepository();
    BarrierRepository barrierRepository = new BarrierRepository();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {

        Room room = new Room();
        room.setRoomID(UUID.randomUUID());
        room.setHeight(height);
        room.setWidth(width);
        List<Room> rooms = roomRepository.getRooms();
        rooms.add(room);
        roomRepository.setRooms(rooms);
        return room.getRoomID();

    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {

        List<Barrier> barriers = barrierRepository.getBarriers();
        barriers.add(barrier);
        if ( barrier.getStart().getX().equals(barrier.getEnd().getX())){
            roomRepository.getRoom(roomId).getVertikalBarriers().add(barrier);
        }else {
            roomRepository.getRoom(roomId).getHorizontalBarriers().add(barrier);
        }
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
        Connection connection = new Connection();
        connection.setSourceRoomID(sourceRoomId);
        connection.setDestinationRoomID(destinationRoomId);
        connection.setSourceCoordinates(sourceCoordinate);
        connection.setDestinationCoordinates(destinationCoordinate);
        connection.setConnectionID(UUID.randomUUID());
        List<Connection> connections = connectionRepository.getConnections();
        connections.add(connection);
        connectionRepository.setConnections(connections);
        return connection.getConnectionID();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot();
        tidyUpRobot.setName(name);
        tidyUpRobot.setTidyUpRobotID(UUID.randomUUID());
        List<TidyUpRobot> tidyUpRobots = tidyUpRobotRepository.getTidyUpRobots();
        tidyUpRobots.add(tidyUpRobot);
        tidyUpRobotRepository.setTidyUpRobots(tidyUpRobots);
        return tidyUpRobot.getTidyUpRobotID();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        RobotMovement robotMovement = new RobotMovement();
        return robotMovement.executeCommand( tidyUpRobotId , command , tidyUpRobotRepository , roomRepository , connectionRepository );
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){ return tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotId).getRoomID();}

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId){ return tidyUpRobotRepository.getTidyUpRobot(tidyUpRobotId).getCoordinate();}
}
