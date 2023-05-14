package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Creatable {
    UUID createConnection(UUID sourceSpaceShipDeckId, Point sourcePoint,
                          UUID destinationSpaceShipDeckId, Point destinationPoint);

    UUID createSpaceShipDeck(Integer height, Integer width);

    void createBarrier(UUID spaceShipDeck, Barrier barrier);

    UUID createMaintenanceDroid(String name);

    UUID createTransportTechnology(String technology);

}
