package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;


import java.util.UUID;

public interface Creatable {

    UUID createConnection(UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint);
    UUID createSpaceShipDeck(Integer height, Integer width);
    UUID createMaintenanceDroid(String name);
    void createBarrier(UUID spaceShipDeck, Barrier barrier);
}
