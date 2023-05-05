package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.exceptions.WrongOrderException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SelfCreatedTests extends MovementTests {

    private MaintenanceDroidService service;

    @BeforeEach
    public void createWorld(){
        service = new MaintenanceDroidService();
        createWorld(service);

    }

    @Test
    public void moveOutOfBoudsTest(){

        assertThrows(RuntimeException.class, () -> executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("en," + spaceShipDeck3 + "]"),
                new Order("[so,1]")
        }));

        assertThrows(RuntimeException.class, () -> executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("en," + spaceShipDeck3 + "]"),
                new Order("[we,1]")
        }));

        assertThrows(RuntimeException.class, () -> executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("en," + spaceShipDeck3 + "]"),
                new Order("[no,4]")
        }));

        assertThrows(RuntimeException.class, () -> executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("en," + spaceShipDeck3 + "]"),
                new Order("[ea,4]")
        }));



    }
    @Test
    public void connectionOutOfBoudsTest(){


        assertThrows(RuntimeException.class, () -> service.addConnection(spaceShipDeck3, new Coordinate(-1, -3), spaceShipDeck1 ,  new Coordinate(1,2)));

    }
    @Test
    public void wallOutOfBoudsTest(){


        assertThrows(RuntimeException.class, () -> service.addWall(spaceShipDeck3, new Wall(new Coordinate(-1, -1), new Coordinate(-1, 3))));

    }
}
