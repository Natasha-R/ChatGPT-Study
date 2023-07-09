package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2Tests extends MovementTests {

    @Test
    public void hittingCleaningDeviceInterruptMove() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        assertTrue(service.executeCommand(cleaningDevice1, new Order("[en," + space1 + "]")));
        assertTrue(service.executeCommand(cleaningDevice1, new Order("[no,1]")));

        assertTrue(service.executeCommand(cleaningDevice2, new Order("[en," + space1 + "]")));
        assertTrue(service.executeCommand(cleaningDevice2, new Order("[no,2]")));

        assertPosition(service, cleaningDevice2, space1, 0, 0);
    }

    @Test
    public void moveOutOfBoundsTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        assertTrue(service.executeCommand(cleaningDevice1, new Order("[en," + space1 + "]")));
        assertTrue(service.executeCommand(cleaningDevice1, new Order("[so,2]")));
        assertPosition(service, cleaningDevice1, space1, 0, 0);
    }

    @Test
    public void cleaningDeviceTransportWithoutConnection() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        assertTrue(service.executeCommand(cleaningDevice1, new Order("[en," + space1 + "]")));
        assertTrue(service.executeCommand(cleaningDevice1, new Order("[no,1]")));
        assertTrue(service.executeCommand(cleaningDevice1, new Order("[ea,1]")));
        assertFalse(service.executeCommand(cleaningDevice1, new Order("[tr," + space1 + "]")));
        assertTrue(service.executeCommand(cleaningDevice2, new Order("[en," + space1 + "]")));
        assertTrue(service.executeCommand(cleaningDevice2, new Order("[no,1]")));
        assertTrue(service.executeCommand(cleaningDevice2, new Order("[ea,1]")));
        assertFalse(service.executeCommand(cleaningDevice2, new Order("[tr," + space1 + "]")));
    }
}
