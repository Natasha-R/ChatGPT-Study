package thkoeln.st.st2praktikum.exercise.E2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2_1MoveInAnotherDeviceTest extends MovementTests {

    CleaningDeviceService service;

    UUID largeEmptySpace;
    UUID cleaningDevice3;
    UUID cleaningDevice4;

    @BeforeEach
    private void init() {
        service = new CleaningDeviceService();
        createWorld(service);

        largeEmptySpace = service.addSpace(10, 10);
        cleaningDevice3 = service.addCleaningDevice("edward");
        cleaningDevice4 = service.addCleaningDevice("trudi");
    }

    @Test
    public void deviceCloselyFollowsAnotherDeviceTest () {
        service.executeCommand(cleaningDevice1, new Task("[en," + largeEmptySpace + "]"));
        service.executeCommand(cleaningDevice1, new Task("[ea,1]"));
        service.executeCommand(cleaningDevice2, new Task("[en," + largeEmptySpace + "]"));

        service.executeCommand(cleaningDevice1, new Task("[no,6]"));
        service.executeCommand(cleaningDevice2, new Task("[ea,1]"));
        service.executeCommand(cleaningDevice2, new Task("[no,5]"));

        service.executeCommand(cleaningDevice1, new Task("[ea,6]"));
        service.executeCommand(cleaningDevice2, new Task("[no,1]"));
        service.executeCommand(cleaningDevice2, new Task("[ea,5]"));

        service.executeCommand(cleaningDevice1, new Task("[so,3]"));
        service.executeCommand(cleaningDevice2, new Task("[ea,1]"));
        service.executeCommand(cleaningDevice2, new Task("[so,2]"));

        assertPosition(service, cleaningDevice1, largeEmptySpace, 7, 3);
        assertPosition(service, cleaningDevice2, largeEmptySpace, 7, 4);
    }

    @Test
    public void rightPositionAfterMultipleCollisionsTest () {
        executeTasks(service, cleaningDevice2, new Task[]{
                new Task("[en," + largeEmptySpace + "]"),
                new Task("[ea,7]")
        });
        executeTasks(service, cleaningDevice3, new Task[]{
                new Task("[en," + largeEmptySpace + "]"),
                new Task("[ea,6]"),
                new Task("[no,5]")
        });
        executeTasks(service, cleaningDevice4, new Task[]{
                new Task("[en," + largeEmptySpace + "]"),
                new Task("[ea,3]"),
                new Task("[no,3]")
        });

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + largeEmptySpace + "]"),
                new Task("[ea,8]"),
                new Task("[no,9]"),
                new Task("[so,1]"),
                new Task("[we,5]")
        });

        assertPosition(service, cleaningDevice1, largeEmptySpace, 4, 3);
    }

    @Test
    public void stubbornDevicesCollisionTest () {
        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + largeEmptySpace + "]"),
                new Task("[ea,7]")
        });
        executeTasks(service, cleaningDevice2, new Task[]{
                new Task("[en," + largeEmptySpace + "]"),
                new Task("[ea,7]"),
                new Task("[ea,7]"),
                new Task("[ea,7]"),
                new Task("[we,2]"),
                new Task("[ea,7]"),
                new Task("[ea,7]")
        });
        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[we,7]"),
                new Task("[we,7]"),
                new Task("[we,7]")
        });

        assertPosition(service, cleaningDevice1, largeEmptySpace, 7, 0);
        assertPosition(service, cleaningDevice2, largeEmptySpace, 6, 0);
    }
}
