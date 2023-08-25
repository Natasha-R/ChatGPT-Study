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

        assertTrue(service.executeCommand(cleaningDevice1, new Command("[en," + space1 + "]")));
        assertFalse(service.executeCommand(cleaningDevice2, new Command("[en," + space1 + "]")));
    }

    @Test
    public void moveToAnotherSpaceAndBackTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space2 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + space3 + "]")
        });

        assertPosition(service, cleaningDevice1, space3, 2, 2);

        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[tr," + space2 + "]")
        });

        assertPosition(service, cleaningDevice1, space2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceOnWrongPositionTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        service.executeCommand(cleaningDevice1, new Command("[en," + space1 + "]"));
        assertFalse(service.executeCommand(cleaningDevice1, new Command("[tr," + space2 + "]")));

        assertPosition(service, cleaningDevice1, space1, 0, 0);
    }

    @Test
    public void bumpIntoWallTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space2 + "]"),
                new Command("[ea,2]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
        });

        assertPosition(service, cleaningDevice1, space2, 1, 1);
    }

    @Test
    public void moveTwoCleaningDevicesAtOnceTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,4]")
        });

        executeCommands(service, cleaningDevice2, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(service, cleaningDevice1, space1, 1, 2);
        assertPosition(service, cleaningDevice2, space1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space3 + "]"),
                new Command("[no,5]"),
                new Command("[ea,5]"),
                new Command("[so,5]"),
                new Command("[we,5]"),
                new Command("[no,1]")
        });

        assertPosition(service, cleaningDevice1, space3, 0, 1);
    }

    @Test
    public void traverseAllSpacesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + space2 + "]"),
                new Command("[so,2]"),
                new Command("[ea,1]"),
                new Command("[tr," + space3 + "]"),
                new Command("[we,5]"),
                new Command("[ea,2]"),
                new Command("[tr," + space2 + "]"),
                new Command("[no,4]"),
                new Command("[ea,3]")
        });

        assertPosition(service, cleaningDevice1, space2, 4, 1);
    }
}
