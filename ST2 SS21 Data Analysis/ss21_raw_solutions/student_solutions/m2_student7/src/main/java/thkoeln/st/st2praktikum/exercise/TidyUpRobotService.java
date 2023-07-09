package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


public class TidyUpRobotService {

    private List<Room> rooms = new ArrayList<Room>();
    private List<Connection> connections = new ArrayList<Connection>();
    private List<Robot> robots = new ArrayList<Robot>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) throws Vector2DException{
        Room room = new Room(height, width);
        rooms.add(room);
        return room.getId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = getRoomByRoomId(roomId);
        if(room.isCoordinateInBounds(wall.getStart()) && room.isCoordinateInBounds(wall.getEnd()))
            room.addBlocker(wall);
        else
            throw new WallException("wall out of bounds");
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
        Room sourceRoom = getRoomByRoomId(sourceRoomId);
        Room destinationRoom = getRoomByRoomId(destinationRoomId);
        Connection connection = new Connection(sourceRoom, sourceVector2D, destinationRoom, destinationVector2D);

        if(sourceRoom.isCoordinateInBounds(sourceVector2D) && destinationRoom.isCoordinateInBounds(destinationVector2D)) {
            connections.add(connection);
            return connection.getId();
        }else {
            throw new Vector2DException("connection out of bounds");
        }
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        Robot robot = new Robot(name);
        robots.add(robot);
        return robot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        Robot robot = getRobotByRobotId(tidyUpRobotId);

        switch (task.getTaskType()){
            case TRANSPORT:
                return transportRobot(robot, task);
            case ENTER:
                return placeRobot(robot, task);
            default:
                return moveRobot(robot, task);
        }
    }

    private Boolean transportRobot(Robot robot, Task task){
        Room destinationRoom = getRoomByRoomId(task.getGridId());

        Optional<Connection> possibleConnections = findPossibleConnections(destinationRoom, robot);

        if(possibleConnections.isPresent() && destinationNotOccupied(destinationRoom, possibleConnections.get().getSourceCoordinates(), robot)){
            robot.setRoom(destinationRoom);
            robot.move(possibleConnections.get().getDestinationCoordinates());
            destinationRoom.addBlocker(robot);
            possibleConnections.get().getSourceRoom().removeBlocker(robot);
            return true;
        }else
            return false;
    }

    private Optional<Connection> findPossibleConnections(Room destinationRoom, Robot robot){
        Stream<Connection> possibleConnectionsStream = connections.stream();

        possibleConnectionsStream = possibleConnectionsStream.filter(findConnection ->
                findConnection.getSourceCoordinates().equals(robot.getCoordinates())
                && findConnection.getSourceRoom() == robot.getRoom()
                && findConnection.getDestinationRoom() == destinationRoom);

        return possibleConnectionsStream.findFirst();
    }

    private Boolean destinationNotOccupied(Room destinationRoom, Vector2D coordinates, Robot robot){
        return robots.stream().noneMatch(findRobot ->
                findRobot.getRoom() == destinationRoom
                && findRobot.getCoordinates().equals(coordinates)
                && findRobot!=robot);
    }

    private Boolean placeRobot(Robot robot, Task task) {
        Room destinationRoom = getRoomByRoomId(task.getGridId());
        if(robot.getRoom() == null && destinationNotOccupied(destinationRoom, new Vector2D(0,0), robot)){
            robot.setRoom(destinationRoom);
            destinationRoom.addBlocker(robot);
            robot.move(new Vector2D(0,0));
            return true;
        }else
            return false;
    }

    private Boolean moveRobot(Robot robot, Task task) {
        Vector2D coordinates = robot.getCoordinates();
        Integer [] movement;
        int steps = task.getNumberOfSteps();
        switch (task.getTaskType()){    //direction
            case NORTH:
                movement = new Integer[]{0, 1};
                break;
            case EAST:
                movement = new Integer[]{1, 0};
                break;
            case SOUTH:
                movement = new Integer[]{0, -1};
                break;
            case WEST:
                movement = new Integer[]{-1, 0};
                break;
            default:
                return true;
        }
        for(int i = 0; i < steps; i++){
            try {
                coordinates = new Vector2D(coordinates.getX() + movement[0],coordinates.getY() + movement[1]);
                Room room = robot.getRoom();
                if(room.isCoordinateInBounds(coordinates) && room.isCoordinateEmpty(coordinates))
                    robot.move(coordinates);
                else
                    break;
            }catch (Vector2DException e){
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
        return getRobotByRobotId(tidyUpRobotId).getRoom().getId();
    }

    /**
     * This method returns the vector2Ds a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector2Ds of the tidy-up robot
     */
    public Vector2D getVector2D(UUID tidyUpRobotId){
        return getRobotByRobotId(tidyUpRobotId).getCoordinates();
    }

    private Room getRoomByRoomId(UUID roomId){
        return rooms.stream().filter(findRoom -> findRoom.getId().equals(roomId)).findAny().get();
    }
    private Robot getRobotByRobotId(UUID robotId){
        return robots.stream().filter(findRobot -> findRobot.getId().equals(robotId)).findAny().get();
    }
}
