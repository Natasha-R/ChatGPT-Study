package thkoeln.st.st2praktikum.exercise.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Order;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E1MyTests extends MovementTests {
    private MaintenanceDroidService service;

    @BeforeEach
    public void createWorld() {
        service = new MaintenanceDroidService();
        createWorld(service);
    }

    @Test
    //Trying to move out of bounds must not be possible // 5x5

    public void TryingToMoveOutOfBoundsMustNotBePossibleTest() {
        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck3 + "]"),
                new Order("[ea,6]"),
        });
        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 2, 0);
        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck3 + "]"),
                new Order("[no,6]"),
        });
        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 2);
        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck3 + "]"),
                new Order("[so,6]"),
        });
        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 0);
        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck3 + "]"),
                new Order("[we,6]"),
        });
        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 0);


    }

    @Test
    //Placing a connection out of bounds must not be possible
    public void PlacingAConnectionOutOfBoundsMustNotBePossible() {
        assertThrows(RuntimeException.class, () -> service.addConnection(spaceShipDeck3, new Coordinate(-1, -3), spaceShipDeck1, new Coordinate(1, 2)));
    }

    @Test
    public void PlacingAObstacleOutOfBoundsMustNotBePossible() {
        assertThrows(RuntimeException.class, () -> service.addObstacle(spaceShipDeck3, new Obstacle(new Coordinate(-1, -1), new Coordinate(-1, 3))));
    }
}
