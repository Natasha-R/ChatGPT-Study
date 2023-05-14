package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TidyUpRobotService {

    List<Room> rooms = new ArrayList<Room>();
    List<Connection> connections = new ArrayList<Connection>();
    List<Robot> robots = new ArrayList<Robot>();
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        rooms.add(new Room(height,width));
        return rooms.get(rooms.size() -1).getId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID roomId, String wallString) {
        rooms.stream().filter(findRoom -> findRoom.getId() == roomId).findAny().ifPresent(foundRoom -> foundRoom.addWall(wallString));
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
        Room sourceRoom = rooms.stream().filter(findRoom -> findRoom.getId() == sourceRoomId).findFirst().get();
        Room destinationRoom = rooms.stream().filter(findRoom -> findRoom.getId() == destinationRoomId).findFirst().get();
        connections.add(new Connection(sourceRoom,sourceCoordinate,destinationRoom,destinationCoordinate));
        return connections.get(connections.size() -1).getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        robots.add(new Robot(name));
        return robots.get(robots.size() -1).getId();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        Robot robot = robots.stream().filter(findRobot -> findRobot.getId() == tidyUpRobotId).findFirst().get();
        String[] command = commandString.replace("[","").replace("]","").split(",");
        switch (command[0]){
            case "tr":
                return transportRobot(robot, command[1]);
            case "en":
                return placeRobot(robot, command[1]);
            default:
                return moveRobot(robot, command[0], command[1]);
        }
    }
    public Boolean moveRobot(Robot robot, String directionString, String stepsString){
        List<Wall> walls = robot.getRoom().getWalls();
        int[] coordinates = robot.coordinates;
        int[] movement;
        int steps = Integer.parseInt(stepsString);
        switch (directionString){    //direction
            case "no":
                movement = new int[]{0, 1};
                break;
            case "ea":
                movement = new int[]{1, 0};
                break;
            case "so":
                movement = new int[]{0, -1};
                break;
            case "we":
                movement = new int[]{-1, 0};
                break;
            default:
                return true;
        }
        for(int i = 0; i < steps; i++){
            if(movement[1] == 1){
                int dest = coordinates [1] + movement[1];
                for (Wall elem: walls) {
                    if(elem.getStartPoint()[1] == elem.getEndPoint()[1] && elem.getEndPoint()[1]  == dest
                            && coordinates[0] >= elem.getStartPoint()[0] && coordinates[0] < elem.getEndPoint()[0]){
                        return true;
                    }
                }
            }if(movement[1] == -1){
                for (Wall elem: walls) {
                    if(elem.getStartPoint()[1] == elem.getEndPoint()[1] && elem.getEndPoint()[1]  == coordinates[1]
                            && coordinates[0] >= elem.getStartPoint()[0] && coordinates[0] < elem.getEndPoint()[0]){
                        return true;
                    }
                }
            }if(movement[0] == 1){
                int dest = coordinates [0] + movement[0];
                for (Wall elem: walls) {
                    if(elem.getStartPoint()[0] == elem.getEndPoint()[0] && elem.getEndPoint()[0]  == dest
                            && coordinates[1] >= elem.getStartPoint()[1] && coordinates[1] < elem.getEndPoint()[1]){
                        return true;
                    }
                }
            }if(movement[0] == -1){
                for (Wall elem: walls) {
                    if(elem.getStartPoint()[0] == elem.getEndPoint()[0] && elem.getEndPoint()[0]  == coordinates[0]
                            && coordinates[1] >= elem.getStartPoint()[1] && coordinates[1] < elem.getEndPoint()[1]){
                        return true;
                    }
                }
            }
            if(coordinates[0] + movement[0] < 0 || coordinates[0] + movement[0] > robot.getRoom().getWidth()-1 || coordinates[1] + movement[1] < 0 || coordinates[1] + movement[1] > robot.getRoom().getHeight()-1)
                return true;  //boundary
            if(robots.stream().anyMatch(findRobot -> findRobot.getRoom() == robot.getRoom() && findRobot.getCoordinates()[0] == robot.getCoordinates()[0] && findRobot.getCoordinates()[1] == robot.getCoordinates()[1] && findRobot != robot))
                return true;// collision check
            coordinates[0] += movement[0];
            coordinates[1] += movement[1];

        }

        robot.move(coordinates);
        return true;
    }

    public Boolean transportRobot(Robot robot, String roomString){
        Room destinationRoom = rooms.stream().filter(findRoom -> findRoom.getId().toString().equals(roomString)).findFirst().get();
        Optional<Connection> possibleConnections = connections.stream().filter(findConnection -> findConnection.getSourceCoordinates()[0] == robot.getCoordinates()[0] &&findConnection.getSourceCoordinates()[1] == robot.getCoordinates()[1] && findConnection.getSourceRoom() == robot.getRoom() && findConnection.getDestinationRoom() == destinationRoom).findFirst();
        if(possibleConnections.isPresent() && robots.stream().noneMatch(findRobot -> findRobot.getRoom() == destinationRoom && findRobot.getCoordinates()[0] == possibleConnections.get().getSourceCoordinates()[0] && findRobot.getCoordinates()[1] == possibleConnections.get().getSourceCoordinates()[1] && findRobot!=robot)){
            robot.move(possibleConnections.get().getDestinationCoordinates());
            robot.setRoom(destinationRoom);
            return true;
        }else
            return false;
    }

    public Boolean placeRobot(Robot robot, String roomString){
        Room destinationRoom = rooms.stream().filter(findRoom -> findRoom.getId().toString().equals(roomString)).findFirst().get();
        if(robot.getRoom() == null && robots.stream().noneMatch(findRobot -> findRobot.getRoom() == destinationRoom && findRobot.getCoordinates()[0] == robot.getCoordinates()[0] && findRobot.getCoordinates()[1] == robot.getCoordinates()[1])){
            robot.setRoom(destinationRoom);
            robot.move(new int[]{0,0});
            return true;
        }else
            return false;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return robots.stream().filter(findRobot -> findRobot.getId() == tidyUpRobotId).findFirst().get().getRoom().getId();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId){
        int[] coordinates = robots.stream().filter(findRobot -> findRobot.getId() == tidyUpRobotId).findFirst().get().getCoordinates();
        return "("+coordinates[0]+","+coordinates[1]+")";
    }
}
