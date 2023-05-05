package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class E1MovementTests extends MovementTests {

    @Test
    public void cleaningDeviceSpawnOnSamePositionTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        assertTrue(service.executeCommand(cleaningDevice1, "[en," + space1 + "]"));
        assertFalse(service.executeCommand(cleaningDevice2, "[en," + space1 + "]"));
    }

    @Test
    public void moveToAnotherSpaceAndBackTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new String[]{
                "[en," + space2 + "]",
                "[ea,1]",
                "[tr," + space3 + "]"
        });

        assertPosition(service, cleaningDevice1, space3, 2, 2);

        executeCommands(service, cleaningDevice1, new String[]{
                "[tr," + space2 + "]"
        });

        assertPosition(service, cleaningDevice1, space2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceOnWrongPositionTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        service.executeCommand(cleaningDevice1, "[en," + space1 + "]");
        assertFalse(service.executeCommand(cleaningDevice1, "[tr," + space2 + "]"));

        assertPosition(service, cleaningDevice1, space1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new String[]{
                "[en," + space2 + "]",
                "[ea,2]",
                "[no,3]",
                "[we,1]"
        });

        assertPosition(service, cleaningDevice1, space2, 1, 1);
    }

    @Test
    public void moveTwoCleaningDevicesAtOnceTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new String[]{
                "[en," + space1 + "]",
                "[ea,1]",
                "[no,4]"
        });

        executeCommands(service, cleaningDevice2, new String[]{
                "[en," + space1 + "]",
                "[ea,2]",
                "[no,5]"
        });

        assertPosition(service, cleaningDevice1, space1, 1, 2);
        assertPosition(service, cleaningDevice2, space1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new String[]{
                "[en," + space3 + "]",
                "[no,5]",
                "[ea,5]",
                "[so,5]",
                "[we,5]",
                "[no,1]"
        });

        assertPosition(service, cleaningDevice1, space3, 0, 1);
    }

    @Test
    public void traverseAllSpacesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new String[]{
                "[en," + space1 + "]",
                "[no,1]",
                "[ea,1]",
                "[tr," + space2 + "]",
                "[so,2]",
                "[ea,1]",
                "[tr," + space3 + "]",
                "[we,5]",
                "[ea,2]",
                "[tr," + space2 + "]",
                "[no,4]",
                "[ea,3]"
        });

        assertPosition(service, cleaningDevice1, space2, 4, 1);
    }
}
