package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Creatable {
    UUID createConnection(UUID sourceSpaceDeckId, Point sourcePoint,
                          UUID destinationSpaceDeckId, Point destinationPoint);

    UUID createSpaceDeck(Integer height, Integer width);

    void createBarrier(UUID spaceDeck, Barrier barrier);

    UUID createMaintenanceDroid(String name);

}
