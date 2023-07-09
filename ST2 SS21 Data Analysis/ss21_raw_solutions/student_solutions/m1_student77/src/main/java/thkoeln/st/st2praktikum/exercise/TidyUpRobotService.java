package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobotService {
    private HashMap<UUID, Room> rooms = new HashMap<>();
    private HashMap<UUID, TidyUpRobot> tidyUpRobots = new HashMap();

    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height, width);
        UUID id = room.getId();
        this.rooms.put(id,room);
        return id;
    }


    public void addBarrier(UUID roomId, String barrierString) {
        Room room = this.rooms.get(roomId);
        room.addBarrier(barrierString);
    }

    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        Connection connection = new Connection(sourceRoomId, sourceCoordinate, destinationRoomId, destinationCoordinate);
        Room sourceRoom = this.rooms.get(sourceRoomId);
        Room destinationRoom = this.rooms.get(destinationRoomId);
        sourceRoom.addConnection(connection);
        destinationRoom.addConnection(connection);
        return connection.getId();
    }

    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name, this.rooms);
        UUID id = tidyUpRobot.getId();
        this.tidyUpRobots.put(id, tidyUpRobot);
        return id;
    }

    public Boolean executeCommand(UUID tidyUpRobotId, String commandString) {
        return this.tidyUpRobots.get(tidyUpRobotId).execute(commandString);
    }

    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return this.tidyUpRobots.get(tidyUpRobotId).getTidyUpRobotRoomId();
    }

    public String getCoordinates(UUID tidyUpRobotId){
        return this.tidyUpRobots.get(tidyUpRobotId).getCoordinates();
    }
}
