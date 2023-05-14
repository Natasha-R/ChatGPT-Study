package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValueObjectTests extends MovementTests{
    private MaintenanceDroidService service;

    @BeforeEach
    public void setUp() {
        service = new MaintenanceDroidService();
        createWorld(service);
    }

    @Test
    public void bumpIntoBoundsTest() {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[so,1]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    public void wallOutOfBoundsTest() {
        assertThrows(RuntimeException.class, () -> new Wall(new Vector2D(-1, 1 ), new Vector2D(2, 1 )));
        assertThrows(RuntimeException.class, () -> service.addWall(spaceShipDeck1, new Wall(new Vector2D(5, 5 ), new Vector2D(5, 7 ))));
    }

    @Test
    public void traversingOnAnOccupiedDestination() {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[no,1]"),
                new Task("[ea,1]"),
                new Task("[tr," + spaceShipDeck2 + "]")
        });

        executeTasks(service, maintenanceDroid2, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[ea,1]"),
                new Task("[no,1]"),
                new Task("[tr," + spaceShipDeck2 + "]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 0, 1);
        assertPosition(service, maintenanceDroid2, spaceShipDeck1, 1, 1);
    }
}
