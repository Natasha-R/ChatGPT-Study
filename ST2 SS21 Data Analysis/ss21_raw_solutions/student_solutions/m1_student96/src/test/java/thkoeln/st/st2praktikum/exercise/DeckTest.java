package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.core.TestWorld;
import thkoeln.st.st2praktikum.exercise.droids.Droid;
import thkoeln.st.st2praktikum.exercise.map.Coordinates;
import thkoeln.st.st2praktikum.exercise.map.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest extends TestWorld {
    MaintenanceDroidService service = new MaintenanceDroidService();

    @Test
    public void addDeckTest(){
        createWorld(service);
        int size = service.controller.getNodes(spaceShipDeck0).size();
        assertEquals(8, size);
    }

    @Test
    public void getNodesTest(){
        createWorld(service);
        Coordinates testCoordinates = new Coordinates(2,0);

        Node testNode = service.controller.getEnv().getNode(spaceShipDeck0, "(2,0)");
        assertEquals(testCoordinates, testNode.getCoordinates());
        assertEquals("(3,0)", testNode.getEast().getCoordinates().toString());
    }

    @Test
    public void addBarrierTest(){
        createWorld(service);
        Droid arvin = service.controller.getDroidByUUID(maintenanceDroid0);

        service.controller.executeCommand(maintenanceDroid0, "(en," + spaceShipDeck0 + ")");
        assertEquals("(0,0)",arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0,arvin.getPosition().getDeckID());

        service.controller.executeCommand(maintenanceDroid0, "(ea,4)");
        assertEquals("(3,0)",arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0,arvin.getPosition().getDeckID());


        service.controller.executeCommand(maintenanceDroid0, "(no,1)");
        assertEquals("(3,1)",arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0,arvin.getPosition().getDeckID());

        service.addBarrier(spaceShipDeck0,"(2,0)-(2,1)");

        service.controller.executeCommand(maintenanceDroid0, "(we,3)");
        assertEquals("(3,1)",arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0,arvin.getPosition().getDeckID());
    }

    @Test
    public void moveOutOfBounds() {
        createWorld(service);
        Droid arvin = service.controller.getDroidByUUID(maintenanceDroid0);

        service.controller.executeCommand(maintenanceDroid0, "(en," + spaceShipDeck0 + ")");
        assertEquals("(0,0)", arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0, arvin.getPosition().getDeckID());

        service.controller.executeCommand(maintenanceDroid0, "(no,1)");
        assertEquals("(0,1)", arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0, arvin.getPosition().getDeckID());

        service.controller.executeCommand(maintenanceDroid0, "(we,1)");
        assertEquals("(0,1)", arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0, arvin.getPosition().getDeckID());
    }
}
