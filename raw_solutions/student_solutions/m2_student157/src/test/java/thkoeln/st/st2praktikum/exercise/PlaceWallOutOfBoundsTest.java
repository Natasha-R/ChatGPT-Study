package thkoeln.st.st2praktikum.exercise;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlaceWallOutOfBoundsTest {
    UUID spaceShipDeck1;
    MaintenanceDroidService service;

    @Before
    public void createWorld() {
        service = new MaintenanceDroidService();
        spaceShipDeck1 = service.addSpaceShipDeck(10,10);
    }

    @Test
    public void TestPositive() {
        assertThrows(RuntimeException.class, () -> service.addWall(spaceShipDeck1, new Wall("(11,0)-(0,0)")));
    }

    @Test
    public void TestNegative() {
        assertThrows(RuntimeException.class, () -> service.addWall(spaceShipDeck1, new Wall("(-1,0)-(0,0)")));
    }
}
