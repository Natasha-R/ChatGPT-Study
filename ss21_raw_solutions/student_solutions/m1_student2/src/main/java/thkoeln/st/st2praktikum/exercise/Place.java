package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Place implements PlaceInterface{
    private TidyUpRobot tidyUpRobot;
    private Room room;
    private Connection connection;
    private HashMap<UUID, TidyUpRobot> tidyUpRobots;
    private HashMap<UUID, Room> rooms;
    private HashMap<UUID, Connection> connections;

    public Place() {
        this.tidyUpRobots = new HashMap<UUID, TidyUpRobot>();
        this.rooms = new HashMap<UUID, Room>();
        this.connections = new HashMap<UUID, Connection>();
    }

    @Override
    public UUID setRoom(Integer height, Integer width) {
        room = new Room(height, width);

        rooms.put(room.getRoomId(),room);
        return room.getRoomId();
    }

    @Override
    public UUID setTidyUpRobot(String name) {
        tidyUpRobot = new TidyUpRobot(name);

        tidyUpRobots.put(tidyUpRobot.getRobotId(),tidyUpRobot);
        return tidyUpRobot.getRobotId();
    }

    @Override
    public UUID setConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        connection = new Connection(sourceRoomId, sourceCoordinate, destinationRoomId, destinationCoordinate);

        connections.put(connection.getConnectionId(),connection);
        rooms.get(sourceRoomId).addConnections(connection);
        rooms.get(destinationRoomId).addConnections(connection);
        return connection.getConnectionId();
    }

    @Override
    public void setObstacle(UUID roomId, String obstacleString) {
        rooms.get(roomId).addObstacle(obstacleString);
    }

    @Override
    public String getTidyUpRobotCoordinate(UUID tidyUpRobotId) {
        return tidyUpRobots.get(tidyUpRobotId).getCoordinate().toString();
    }

    @Override
    public UUID getTidyUpRobotRoom(UUID tidyUpRobotRoomId) {
        return rooms.get(tidyUpRobots.get(tidyUpRobotRoomId).getRobotRoomId()).getRoomId();
    }

    @Override
    public Boolean applyRobotCommand(UUID robotId, String command) throws IllegalArgumentException{
        if (command.contains("en")) {
            UUID roomId = UUID.fromString(command.replaceAll(".*,","").replace("]",""));
            if (rooms.get(roomId).getCell()[0][0].getCellStateisFree() == Boolean.TRUE) {
                return tidyUpRobots.get(robotId).roomInitialize(rooms.get(roomId), command);
            }
        }
        else if (command.contains("tr")) {
            UUID roomId = UUID.fromString(command.replaceAll(".*,","").replace("]",""));
            String destinationCoordinate = rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()).getConnection(roomId).getSourceCoordinate();
            if (destinationCoordinate.equals(tidyUpRobots.get(robotId).getCoordinate().toString())) {
                return tidyUpRobots.get(robotId).transportTo(rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()), rooms.get(roomId), command);
            }
        }
        else if (command.contains("ea")){
            return tidyUpRobots.get(robotId).moveEast(rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()),command);
        }
        else if(command.contains("no")){
            return tidyUpRobots.get(robotId).moveNorth(rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()),command);
        }
        else if(command.contains("so")){
            return tidyUpRobots.get(robotId).moveSouth(rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()),command);
        }
        else if(command.contains("we")){
            return tidyUpRobots.get(robotId).moveWest(rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()),command);
        }
        else{
            throw new IllegalArgumentException("Invalid command");
        }

        return Boolean.FALSE;
    }
}
