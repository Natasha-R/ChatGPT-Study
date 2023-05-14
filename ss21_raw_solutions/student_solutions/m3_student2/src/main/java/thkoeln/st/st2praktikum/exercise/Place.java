package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.UUID;

@Component
@Transactional
public class Place implements PlaceInterface {
    private Room room;
    private TidyUpRobot tidyUpRobot;
    private Connection connection;
    private TransportSystem transportSystem;
    private HashMap<UUID, Room> rooms;
    private HashMap<UUID, TidyUpRobot> tidyUpRobots;
    private HashMap<UUID, Connection> connections;
    private HashMap<UUID, TransportSystem> transportSystems;
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TransportSystemRepository transportSystemRepository;

    public Place(){
        this.rooms = new HashMap<UUID, Room>();
        this.tidyUpRobots = new HashMap<UUID, TidyUpRobot>();
        this.connections = new HashMap<UUID, Connection>();
        this.transportSystems = new HashMap<UUID, TransportSystem>();
    }

    @Override
    public UUID setRoom(Integer height, Integer width) {
        room = new Room();
        room.setHeight(height);
        room.setWidth(width);

        rooms.put(room.getId(),room);
        roomRepository.save(room);
        return room.getId();
    }

    @Override
    public UUID setTidyUpRobot(String name) {
        tidyUpRobot = new TidyUpRobot();
        tidyUpRobot.setName(name);

        tidyUpRobots.put(tidyUpRobot.getId(), tidyUpRobot);
        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot.getId();
    }

    @Override
    public UUID setTransportSystem(String system) {
        transportSystem = new TransportSystem(system);

        transportSystems.put(transportSystem.getId(), transportSystem);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getId();
    }

    @Override
    public UUID setConnection(UUID transportSystemId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {
        connection = new Connection(transportSystemId, sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);

        connections.put(connection.getId(), connection);
        rooms.get(sourceRoomId).addConnections(connection);
        rooms.get(destinationRoomId).addConnections(connection);
        transportSystems.get(transportSystemId).addConnection(connection);
        return connection.getId();
    }

    @Override
    public void setObstacle(UUID roomId, Obstacle obstacle) {
        rooms.get(roomId).addObstacles(obstacle);
    }

    @Override
    public Vector2D getTidyUpRobotCoordinate(UUID tidyUpRobotId) {
        return tidyUpRobots.get(tidyUpRobotId).getVector2D();
    }

    @Override
    public UUID getTidyUpRobotRoom(UUID tidyRobotId) {
        return rooms.get(tidyUpRobots.get(tidyRobotId).getRoomId()).getId();
    }

    @Override
    public Boolean applyRobotCommand(UUID robotId, Command command) throws IllegalArgumentException {
        TidyUpRobot robot = tidyUpRobotRepository.findById(robotId).get();
        tidyUpRobots.get(robotId).addCommand(command);
        Boolean val;

        if(command.getCommandType().equals(CommandType.ENTER)){
            val = tidyUpRobots.get(robotId).roomInitialize(rooms.get(command.getGridId()), command);
            copyRobot(robot,tidyUpRobots.get(robotId));
        }
        else if(command.getCommandType().equals(CommandType.TRANSPORT)){
            Vector2D destinationCoordinate = rooms.get(tidyUpRobots.get(robotId).getRoomId()).getConnection(command.getGridId()).getSourceCoordinate();
            if (destinationCoordinate.equals(tidyUpRobots.get(robotId).getVector2D())){
                val = tidyUpRobots.get(robotId).transportTo(rooms.get(tidyUpRobots.get(robotId).getRoomId()), rooms.get(command.getGridId()), command);
                copyRobot(robot,tidyUpRobots.get(robotId));
                return val;
            }
            else {
                val = Boolean.FALSE;
                copyRobot(robot,tidyUpRobots.get(robotId));
            }
        }
        else if(command.getCommandType().equals(CommandType.EAST) || command.getCommandType().equals(CommandType.NORTH)
               || command.getCommandType().equals(CommandType.SOUTH) || command.getCommandType().equals(CommandType.WEST)){
            val = tidyUpRobots.get(robotId).robotCommandMove(rooms.get(tidyUpRobots.get(robotId).getRoomId()), command);
            copyRobot(robot,tidyUpRobots.get(robotId));
        }
        else
            throw new IllegalArgumentException("invalid command");
        return val;
    }

    private void copyRobot(TidyUpRobot robotCopy, TidyUpRobot robot){
        robotCopy = robot;
        tidyUpRobotRepository.save(robotCopy);
    }
}
