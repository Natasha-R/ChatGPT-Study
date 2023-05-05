package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Exercise2Tests extends E1MovementTests {

    @BeforeEach
    public void setUp() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);
    }
    @Test
    public void placingObstacleOutOfBounds() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        spaceShipDeck1 = service.addSpaceShipDeck(6,6);
        spaceShipDeck2 = service.addSpaceShipDeck(5,5);
        spaceShipDeck3 = service.addSpaceShipDeck(3,3);


        maintenanceDroid1 = service.addMaintenanceDroid("marvin");
        maintenanceDroid2 = service.addMaintenanceDroid("darwin");

        assertThrows(RuntimeException.class, () -> service.addObstacle(spaceShipDeck1,new Obstacle("(7,9)-(11,9)")));

    }
    @Test
    public void placingConnectionOutOfBounds() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        spaceShipDeck1 = service.addSpaceShipDeck(6,6);
        spaceShipDeck2 = service.addSpaceShipDeck(5,5);
        spaceShipDeck3 = service.addSpaceShipDeck(3,3);


        maintenanceDroid1 = service.addMaintenanceDroid("marvin");
        maintenanceDroid2 = service.addMaintenanceDroid("darwin");

        service.addObstacle(spaceShipDeck1, new Obstacle("(0,3)-(2,3)"));
        service.addObstacle(spaceShipDeck1, new Obstacle("(3,0)-(3,3)"));
        service.addObstacle(spaceShipDeck2, new Obstacle("(0,2)-(4,2)"));

        assertThrows(RuntimeException.class, () -> service.addConnection(spaceShipDeck1,new Vector2D("(4,6)"), spaceShipDeck2, new Vector2D("(20,20)")));

    }
   @Test
    public void usingTRcommandMustNotBePossibleIfThereIsNoOutgoingConncectionOnTheCurrentPosition () {
       MaintenanceDroidService service = new MaintenanceDroidService();
       createWorld(service);

       assertFalse(service.executeCommand(maintenanceDroid1, new Task("[tr," + spaceShipDeck2 + "]")));


   }




}
