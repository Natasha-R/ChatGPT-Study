package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Createable {

    UUID createConnection(UUID sourceSpaceShipDeckId, Point sourceCoordinate,
                          UUID destinationSpaceShipDeckId, Point destinationCoordinate);

    UUID createSpaceShipDeck(Integer height, Integer width);

    UUID createMaintenanceDroid(String name);

    void createBarrier(UUID spaceShipDeckId, String barrier);
}
