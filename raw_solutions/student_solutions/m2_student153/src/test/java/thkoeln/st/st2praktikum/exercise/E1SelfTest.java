package thkoeln.st.st2praktikum.exercise;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1SelfTest extends MovementTests {


    @Test
    public void TryingToMoveOutOfBounce() {

        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck3 + "]"),
                new Order("[so,5]"),
                new Order("[we,5]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 0);
    }


    @Test
    public void ConnectionBlocked() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck2 + "]"),
                new Order("[no,1]"),


        });
        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 0, 1);

        executeOrders(service, maintenanceDroid2, new Order[]{
                new Order("[en," + spaceShipDeck1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,1]"),
                new Order("[tr," + spaceShipDeck2 + "]"),


        });
        assertPosition(service, maintenanceDroid2, spaceShipDeck1, 1, 1);
    }


    @Test
    public void WallOutOfBounds() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);
        service.addWall(spaceShipDeck3, new Wall("(1,0)-(9,0)"));
        assertThrows(RuntimeException.class, () -> new Wall(new Vector2D(1, 1 ), new Vector2D(2, 2 )));

    }

    @Test
    public void noTrCommandIfnoPortal() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck1 + "]"),
                new Order("[tr," + spaceShipDeck2 + "]"),

        });
        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 0);
    }
}