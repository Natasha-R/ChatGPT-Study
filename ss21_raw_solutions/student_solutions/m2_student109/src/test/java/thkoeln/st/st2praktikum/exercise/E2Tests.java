package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2Tests {

    protected MaintenanceDroidService maintenanceDroidService ;

    protected UUID spaceShipDeck1;
    protected UUID spaceShipDeck2;

    protected UUID maintenanceDroid1;

    protected Vector2D vector1;
    protected Vector2D vector2;
    protected Vector2D vector3;
    protected Vector2D vector4;

    protected Obstacle obstacle1;

    @BeforeEach
    public void setUp(){
        maintenanceDroidService = new MaintenanceDroidService();

        spaceShipDeck1 = maintenanceDroidService.addSpaceShipDeck(6,6);
        spaceShipDeck2 = maintenanceDroidService.addSpaceShipDeck(5,5);

        maintenanceDroid1 = maintenanceDroidService.addMaintenanceDroid("franki");

        vector1 = new Vector2D(7,7);
        vector2 = new Vector2D(1, 0);
        vector3 = new Vector2D(3,7);
        vector4 =  new Vector2D(4,7);

        obstacle1 = new Obstacle(vector3, vector4);
    }

    @Test
    public void placingAConnectionOutOfBoundsTest(){
        assertThrows(RuntimeException.class, () -> maintenanceDroidService.addConnection(spaceShipDeck1, vector1  , spaceShipDeck2, vector2));
    }

    @Test
    public void placingAObstacleOutOfBoundsTest(){
        assertThrows(RuntimeException.class, () -> maintenanceDroidService.addObstacle(spaceShipDeck1,obstacle1));
    }

    @Test
    public void moveOutOfBoundsNotBePossibleTest(){
        executeTasks(maintenanceDroidService, maintenanceDroid1, new Task[] {
                new Task("[en," + spaceShipDeck2 + "]"),
                new Task("[so,5]"),
                new Task("[we,3]"),
        });
        assertPosition(maintenanceDroidService, maintenanceDroid1, spaceShipDeck2, 0, 0);
    }

    protected void assertPosition(MaintenanceDroidService service, UUID maintenanceDroidId, UUID expectedSpaceShipDeckId, int expectedX, int expectedY) {
        assertEquals(expectedSpaceShipDeckId, service.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId));
        assertEquals(new Vector2D(expectedX, expectedY), service.getVector2D(maintenanceDroidId));
    }

    protected void executeTasks(MaintenanceDroidService service, UUID maintenanceDroid, Task[] Tasks) {
        for (Task task : Tasks) service.executeCommand(maintenanceDroid, task);
    }

}
