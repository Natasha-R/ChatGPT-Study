package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class E1Tests {
    
    private CleaningDeviceService service;
    private UUID space;

    @BeforeEach
    public void setUp() {
        service = new CleaningDeviceService();
        space = service.addSpace(6, 6);
    }

    /*
     * Hitting another cleaningDevice during a Move-Command has to interrupt the current movement
     * */
    @Test
    public void hittingAnotherCleaningDeviceTest() {
        final UUID device1 = service.addCleaningDevice("device1");
        final UUID device2 = service.addCleaningDevice("device2");

        service.executeCommand(device1, new Command(CommandType.ENTER, space));
        service.executeCommand(device1, new Command(CommandType.EAST, 2));

        assertEquals(service.getPoint(device1), new Point(2, 0));
        service.executeCommand(device2, new Command(CommandType.ENTER, space));

        final boolean moveResult = service.executeCommand(device2, new Command(CommandType.EAST, 3));

        assertFalse(moveResult);
        assertEquals(service.getPoint(device2), new Point(1, 0));
    }

    /*
     * Placing a connection out of bounds must not be possible
     * */
    @Test
    public void placeConnectionOutOfBoundsTest() {
        final UUID space1 = service.addSpace(5, 6);
        final UUID space2 = service.addSpace(4, 3);
        assertThrows(RuntimeException.class, () -> service.addConnection(space1, new Point(0, 6), space2, new Point(3, 4)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1, new Point(7, 2), space2, new Point(0, 0)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1, new Point(0, 0), space2, new Point(4, 0)));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1, new Point(0, 0), space2, new Point(2, 5)));
    }

    /*
     * Placing a barrier out of bounds must not be possible
     * */
    @Test
    public void placeBarrierOutOfBoundsTest() {
        assertThrows(IllegalArgumentException.class, () -> service.addBarrier(space, new Barrier(new Point(2, 3), new Point(2, 7))));
    }
}
