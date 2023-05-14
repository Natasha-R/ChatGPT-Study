package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Connection.Connection;
import thkoeln.st.st2praktikum.exercise.Robot.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.Room.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameWorld {
    public Map<UUID, TidyUpRobot> tidyUpRobotMap = new HashMap<>();
    public Map<UUID, Room> roomMap = new HashMap<>();
    public Map<UUID, Connection> connectionMap = new HashMap<>();

    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {
        TidyUpRobot tidyUpRobot = getTidyUpRobotById(tidyUpRobotId);

        if(order.getOrderType().equals(OrderType.ENTER)){
            Room room = getRoomById(order.getGridId());
            return tidyUpRobot.enterRoom(room, new Coordinate(0, 0));

        }else if(order.getOrderType().equals(OrderType.TRANSPORT)){
            Room destinationRoom = getRoomById(order.getGridId());
            return tidyUpRobot.changeRoom(destinationRoom);
        }else{
            tidyUpRobot.move(order);
            return true;
        }
    }

    public Room getRoomById(UUID roomId){
        return roomMap.get(roomId);
    }

    public TidyUpRobot getTidyUpRobotById(UUID tidyUpRobotId){
        return tidyUpRobotMap.get(tidyUpRobotId);
    }

    public UUID addConnection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        Room sourceRoom = getRoomById(sourceRoomId);
        Room destinationRoom = getRoomById(destinationRoomId);
        Connection connection = new Connection(sourceRoom, sourceCoordinate, destinationRoom, destinationCoordinate);
        sourceRoom.addConnectionPacket(connection);
        return connection.getId();
    }

    public UUID addTidyUpRobot(String name){
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotMap.put(tidyUpRobot.getId(), tidyUpRobot);
        return tidyUpRobot.getId();
    }

    public UUID addRoom(Integer height, Integer width){
        Room room = new Room(height, width);
        roomMap.put(room.getId(), room);
        return room.getId();
    }
}
