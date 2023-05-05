package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Creatable {
    UUID createRoom(Integer height, Integer width);
    UUID createConnection(UUID sourceRoomId, Pair sourceCoordinate, UUID destinationRoomId, Pair destinationCoordinate);
    UUID createTidyUpRobot(String name);
    void createWall(UUID room, String stringForWall);
}
