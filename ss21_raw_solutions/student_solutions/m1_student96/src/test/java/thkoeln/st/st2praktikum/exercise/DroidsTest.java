package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.core.TestWorld;
import thkoeln.st.st2praktikum.exercise.droids.Droid;
import thkoeln.st.st2praktikum.exercise.map.DeckGraph;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DroidsTest extends TestWorld {

    MaintenanceDroidService service = new MaintenanceDroidService();

    @Test
    public void  addMaintenanceDroidTest(){
        createWorld(service);

        assertEquals(1, service.controller.getEnv().getDroids().size());
        UUID maintenanceDroid4 = service.addMaintenanceDroid("erwin");
        assertEquals(2, service.controller.getEnv().getDroids().size());
        System.out.println(service.controller.getDroidByUUID(maintenanceDroid0).toString());
        System.out.println(service.controller.getDroidByUUID(maintenanceDroid4).toString());


    }

    @Test
    public void spawnTest(){
        createWorld(service);
        DeckGraph deck0 = service.controller.getDeckByID(spaceShipDeck0);
        Droid arwin = service.controller.getDroidByUUID(maintenanceDroid0);
        service.controller.executeCommand(maintenanceDroid0,"(en,"+ spaceShipDeck0 + ")");

        assertEquals(spaceShipDeck0.toString(), deck0.getDeckID().toString());
        assertEquals("(0,0)", arwin.getPosition().getCoordinates().toString());
        assertEquals("(0,0)", arwin.getPosition().getCoordinates().toString());
        assertTrue(arwin.getPosition().getIsBlocked());
        assertFalse(service.controller.executeCommand(maintenanceDroid0,"(en,"+ spaceShipDeck0 + ")"));
    }

    @Test
    public void transportTest(){
        createWorld(service);
        Droid arvin = service.controller.getDroidByUUID(maintenanceDroid0);

        System.out.println("(en," + spaceShipDeck0 + ")");
        service.controller.executeCommand(maintenanceDroid0, "(en," + spaceShipDeck0 + ")");
        assertEquals("(0,0)",arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0,arvin.getPosition().getDeckID());

        System.out.println("(tr," + spaceShipDeck4 + ")");
        service.controller.executeCommand(maintenanceDroid0, "(tr," + spaceShipDeck4 + ")");
        assertEquals(spaceShipDeck4,arvin.getPosition().getDeckID());

        assertEquals("(1,0)",arvin.getPosition().getCoordinates().toString());

    }

    @Test
    public void movementTest(){
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

        service.controller.executeCommand(maintenanceDroid0, "(we,2)");
        assertEquals("(1,1)",arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0,arvin.getPosition().getDeckID());

        service.controller.executeCommand(maintenanceDroid0, "(so,1)");
        assertEquals("(1,0)",arvin.getPosition().getCoordinates().toString());
        assertEquals(spaceShipDeck0,arvin.getPosition().getDeckID());

    }
}
