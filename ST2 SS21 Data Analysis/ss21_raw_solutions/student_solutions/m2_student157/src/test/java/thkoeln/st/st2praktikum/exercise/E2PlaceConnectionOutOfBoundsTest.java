package thkoeln.st.st2praktikum.exercise;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2PlaceConnectionOutOfBoundsTest {

    UUID spaceShipDeck1;
    UUID spaceShipDeck2;
    MaintenanceDroidService service;

    @Before
    public void createWorld() {
        service = new MaintenanceDroidService();
        spaceShipDeck1 = service.addSpaceShipDeck(10, 10);
        spaceShipDeck2 = service.addSpaceShipDeck(10, 10);
    }

    @Test
    public void TestNegative() {
        assertThrows(
                RuntimeException.class,
                () -> service.addConnection(
                        spaceShipDeck1,
                        new Vector2D(-1, 0),
                        spaceShipDeck2,
                        new Vector2D(0, 0)
                ));
    }

    @Test
    public void TestPositive() {
        assertThrows(
                RuntimeException.class,
                () -> service.addConnection(
                        spaceShipDeck1,
                        new Vector2D(11, 0),
                        spaceShipDeck2,
                        new Vector2D(0, 0)
                ));
    }

}
