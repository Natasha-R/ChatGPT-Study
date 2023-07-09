package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.entity.CleaningDevice;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class OwnTests {

    protected void assertPosition(CleaningDeviceService service, UUID cleaningDeviceId, UUID expectedSpaceId, int expectedX, int expectedY) {
        assertEquals(expectedSpaceId, service.getCleaningDeviceSpaceId(cleaningDeviceId));
        assertEquals(new Coordinate(expectedX, expectedY), service.getCoordinate(cleaningDeviceId));
    }

    @Test
    public void moveOutOfBoundsTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        UUID space1 = service.addSpace(4,4);
        UUID device1 = service.addCleaningDevice("device1");
        service.executeCommand(device1,new Task("[en," + space1 +"]"));
        assertPosition(service,device1,space1,0,0);
        service.executeCommand(device1,new Task("[we,1]"));
        service.executeCommand(device1,new Task("[so,1]"));
        assertPosition(service,device1,space1,0,0);
        service.executeCommand(device1,new Task("[no,10]"));
        service.executeCommand(device1,new Task("[we,1]"));
        service.executeCommand(device1,new Task("[no,1]"));
        assertPosition(service,device1,space1,0,3);
        service.executeCommand(device1,new Task("[ea,10]"));
        service.executeCommand(device1,new Task("[ea,1]"));
        service.executeCommand(device1,new Task("[no,1]"));
        assertPosition(service,device1,space1,3,3);
        service.executeCommand(device1,new Task("[so,10]"));
        service.executeCommand(device1,new Task("[ea,1]"));
        service.executeCommand(device1,new Task("[so,1]"));
        assertPosition(service,device1,space1,3,0);
    }

    @Test
    public void hittingAnotherCleaningDeviceTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        UUID space1 = service.addSpace(4,4);
        UUID device1 = service.addCleaningDevice("device1");
        UUID device2 = service.addCleaningDevice("device2");
        service.executeCommand(device1,new Task("[en," + space1 +"]"));
        assertPosition(service,device1,space1,0,0);
        service.executeCommand(device1,new Task("[ea,1]"));
        assertPosition(service,device1,space1,1,0);
        service.executeCommand(device2,new Task("[en," + space1 +"]"));
        assertPosition(service,device2,space1,0,0);
        service.executeCommand(device2,new Task("[ea,1]"));
        assertPosition(service,device2,space1,0,0);
    }

    @Test
    public void connectionOutOfBoundsTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        UUID space1 = service.addSpace(4,4);
        UUID space2 = service.addSpace(4,4);
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(3,4),space2,new Coordinate(2,0)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(7,1),space2,new Coordinate(1,2)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(-1,2),space2,new Coordinate(3,0)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(3,2),space2,new Coordinate(3,-2)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(0,2),space2,new Coordinate(4,0)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Coordinate(0,2),space2,new Coordinate(2,6)));
    }
}
