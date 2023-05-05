package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyTest extends MovementTests {

    @Test
    public void TestMoveOutOfBound() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck3 + "]"),
                new Order("[so,1000000]"),
                new Order("[ea,1000000]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 2, 0);
    }

    @Test
    public void TestTraverseCommandWithoutConnection() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck1 + "]"),
                new Order("[no,6]"),
                new Order("[tr," + spaceShipDeck2 + "]")
            }
        );
        //der Roboter bleibt im shipdeck1
        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 2);
    }


    @Test
    public void TestPlaceConnectionOutOfBound() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);


        assertThrows(UnsupportedOperationException.class, () -> service.addConnection(spaceShipDeck1,
                     new Coordinate("(7,7)"), spaceShipDeck2, new Coordinate("(7,7)")));

    }
}
