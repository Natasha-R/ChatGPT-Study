package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Place implements PlaceInterface {
    private Room room;
    private TidyUpRobot tidyUpRobot;
    private Connection connection;
    private HashMap<UUID, Room> rooms;
    private HashMap<UUID, TidyUpRobot> tidyUpRobots;
    private HashMap<UUID, Connection> connections;

    public Place(){
        this.rooms = new HashMap<UUID, Room>();
        this.tidyUpRobots = new HashMap<UUID, TidyUpRobot>();
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

        tidyUpRobots.put(tidyUpRobot.getRobotId(), tidyUpRobot);
        return tidyUpRobot.getRobotId();
    }

    @Override
    public UUID setConnection(UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {
        connection = new Connection(sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);

        connections.put(connection.getConnectionId(), connection);
        rooms.get(sourceRoomId).addConnections(connection);
        rooms.get(destinationRoomId).addConnections(connection);
        return connection.getConnectionId();
    }

    @Override
    public void setObstacle(UUID roomId, Obstacle obstacle) {
        rooms.get(roomId).addObstacles(obstacle);
    }

    @Override
    public Vector2D getTidyUpRobotCoordinate(UUID tidyUpRobotId) {
        return tidyUpRobots.get(tidyUpRobotId).getCoordinate();
    }

    @Override
    public UUID getTidyUpRobotRoom(UUID tidyUpRobotId) {
        return rooms.get(tidyUpRobots.get(tidyUpRobotId).getRobotRoomId()).getRoomId();
    }

    @Override
    public Boolean applyRobotCommand(UUID robotId, Command command) throws IllegalArgumentException{
        if(command.getCommandType().equals(CommandType.ENTER)){
            if(rooms.get(command.getGridId()).getCell()[0][0].getCellStateIsFree() == Boolean.TRUE){
                return tidyUpRobots.get(robotId).robotCommandInitialize(tidyUpRobots.get(robotId), rooms.get(command.getGridId()), command);
            }
        }
        else if(command.getCommandType().equals(CommandType.TRANSPORT)){
            Vector2D destinationCoordinate = rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()).getConnection(command.getGridId()).getSourceCoordinate();
            if(destinationCoordinate.equals(tidyUpRobots.get(robotId).getCoordinate())){
                return tidyUpRobots.get(robotId).robotCommandTransport(tidyUpRobots.get(robotId), rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()), rooms.get(command.getGridId()), command);
            }
        }
        else if(command.getCommandType().equals(CommandType.EAST) || command.getCommandType().equals(CommandType.NORTH)
                || command.getCommandType().equals(CommandType.SOUTH) || command.getCommandType().equals(CommandType.WEST)){
            return tidyUpRobots.get(robotId).robotCommandMove(tidyUpRobots.get(robotId), rooms.get(tidyUpRobots.get(robotId).getRobotRoomId()), command);
        }
        else{
            throw new IllegalArgumentException("Invalid command");
        }
        return Boolean.FALSE;
    }
}
