package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Creatable {
    UUID createRoom(Integer height, Integer width);
    UUID createTidyUpRobot(String name);
    UUID createConnection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate);
    void createWall(UUID room, Wall wall);
}
