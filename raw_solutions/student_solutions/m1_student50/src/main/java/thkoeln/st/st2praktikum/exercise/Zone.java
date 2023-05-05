package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

/* Zone ist eine Klasse, die viele Rooms enthält und auch die Reinigungsgeräte,
die in jedem Room stehen */
public class Zone implements Creatable {

    public static HashMap<UUID, Room> rooms = new HashMap<>();
    public static HashMap<UUID, TidyUpRobot> tidyUpRobots = new HashMap<>();
    public static HashMap<UUID, Connection> connections = new HashMap<>();
    public static Room fetchRoomWithId(UUID roomId){
        return rooms.get(roomId);
    }

    @Override
    public UUID createRoom(Integer height, Integer width) {
        Room room = new Room();
        room.setWidth(width);
        room.setHeight(height);

        rooms.put(room.getId(), room);

        return room.getId();
    }

    @Override
    public void createWall(UUID room, String stringForWall) {
        fetchRoomWithId(room).addWall(stringForWall);
    }

    @Override
    public UUID createConnection(UUID sourceRoomId, Pair sourceCoordinate, UUID destinationRoomId, Pair destinationCoordinate) {
        Connection connection =  new Connection();

        connection.setSourceRoom(sourceRoomId);
        connection.setDestinationRoom(destinationRoomId);
        connection.setSourceCoordinate(sourceCoordinate);
        connection.setDestinationCoordinate(destinationCoordinate);

        rooms.get(sourceRoomId).connections.add(connection);
        connections.put(connection.getId(), connection);

        return connection.getId();
    }

    @Override
    public UUID createTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot();
        tidyUpRobot.setName(name);
        tidyUpRobots.put(tidyUpRobot.getId(),tidyUpRobot);

        return tidyUpRobot.getId();
    }

    protected UUID getTidyUpRobotRoomId(UUID tidyUpRobot){
        return tidyUpRobots.get(tidyUpRobot).getCurrentRoomUUID();
    }

    public static boolean initialise(UUID tidyUpRobot, String command) {
        UUID roomId = UUID.fromString(command.substring(command.indexOf(',') + 1, command.length() - 1));

        if (rooms.get(roomId).tidyUpRobots.isEmpty()) {
            tidyUpRobots.get(tidyUpRobot).setCurrentRoomUUID(roomId);
            tidyUpRobots.get(tidyUpRobot).setCurrentRoom(rooms.get(roomId));
            rooms.get(roomId).tidyUpRobots.add(tidyUpRobots.get(tidyUpRobot));
            return true;
        }else{
            for (int i = 0; i < rooms.get(roomId).tidyUpRobots.size();i++) {
                if (rooms.get(roomId).tidyUpRobots.get(i).coordinate.getX() == 0 &&
                        rooms.get(roomId).tidyUpRobots.get(i).coordinate.getY() == 0) {
                    return false;
                } else {
                    tidyUpRobots.get(tidyUpRobot).setCurrentRoomUUID(roomId);
                    rooms.get(roomId).tidyUpRobots.add(tidyUpRobots.get(tidyUpRobot));
                    tidyUpRobots.get(tidyUpRobot).setCurrentRoom(rooms.get(roomId));
                    return true;
                }
            }
        }
        return false;
    }

}
