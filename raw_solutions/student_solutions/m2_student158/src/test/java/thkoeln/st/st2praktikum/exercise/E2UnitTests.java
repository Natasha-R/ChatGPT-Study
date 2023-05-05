package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2UnitTests {

    protected UUID space1;

    protected UUID space2;

    protected UUID cleaningDevice;

    protected UUID connection;

    protected UUID wall;

    protected CleaningDeviceService service;


    public void init(){
        service = new CleaningDeviceService();

        space1 = service.addSpace(4,4);
        space2 = service.addSpace(5,5);

        cleaningDevice = service.addCleaningDevice("Deebot T9+");
    }

    @Test
    public void moveOutOfBoundsTest() {
        init();
        service.executeCommand(cleaningDevice, new Task("[en," + space1 + "]"));
        service.executeCommand(cleaningDevice, new Task("[no,5]"));
        assertEquals(service.getCoordinate(cleaningDevice), new Coordinate("(0,3)"));
        service.executeCommand(cleaningDevice, new Task("[so,7]"));
        assertEquals(service.getCoordinate(cleaningDevice), new Coordinate("(0,0)"));
        service.executeCommand(cleaningDevice, new Task("[ea,10]"));
        assertEquals(service.getCoordinate(cleaningDevice), new Coordinate("(3,0)"));
        service.executeCommand(cleaningDevice, new Task("[we,4]"));
        assertEquals(service.getCoordinate(cleaningDevice), new Coordinate("(0,0)"));
    }

    @Test
    public void placeConnectionOutOfBoundsTest(){
        init();
        assertThrows(IllegalArgumentException.class, ()->
                service.addConnection(space1, new Coordinate("(6,2)"), space2, new Coordinate("(1,1)")));
        assertThrows(IllegalArgumentException.class, ()->
                service.addConnection(space1, new Coordinate("(2,6)"), space2, new Coordinate("(1,1)")));
        assertThrows(IllegalArgumentException.class, ()->
                service.addConnection(space1, new Coordinate("(1,1)"), space2, new Coordinate("(2,6)")));
        assertThrows(IllegalArgumentException.class, ()->
                service.addConnection(space1, new Coordinate("(1,1)"), space2, new Coordinate("(6,2)")));


    }

    @Test
    public void placeWallOutOfBoundsTest(){
        init();
        assertThrows(IllegalArgumentException.class, ()->
                service.addWall(space1, new Wall("(6,2)-(1,1)")));

        assertThrows(IllegalArgumentException.class, ()->
                service.addWall(space1, new Wall("(1,1)-(6,2)")));

        assertThrows(IllegalArgumentException.class, ()->
                service.addWall(space1, new Wall("(2,6)-(1,1)")));

        assertThrows(IllegalArgumentException.class, ()->
                service.addWall(space1, new Wall("(1,1)-(2,6)")));

    }


}
