package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2ExtendedMovementTests extends MovementTests{

    @Test
    public void movementStoppedByRobotInCurrentPath() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[ea,2]")
        });
        executeTasks(service, tidyUpRobot2, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[ea,3]")
        });

        assertPosition(service, tidyUpRobot1, room2, 2, 0);
        assertPosition(service, tidyUpRobot2, room2, 1, 0);
    }

    @Test
    public void noMovementOutOfBoundsAllowed() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[so,1]"),
                new Task("[we,1]")
        });
        assertPosition(service, tidyUpRobot1, room1, 0, 0);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[no,1]"),
                new Task("[ea,1]"),
                new Task("[tr,"+ room2 +"]")
        });
        assertTrue(service.executeCommand(tidyUpRobot1, new Task("[ea,5]")));
        assertPosition(service, tidyUpRobot1, room2, 4, 1);
    }

    @Test
    public void noTransportAllowedWithoutUsableConnection() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        service.executeCommand(tidyUpRobot1, new Task("[en," + room1 + "]"));
        assertFalse(service.executeCommand(tidyUpRobot1, new Task("[tr," + room2 + "]")));

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[no,1]"),
                new Task("[ea,1]")
        });
        assertTrue(service.executeCommand(tidyUpRobot1, new Task("[tr," + room2 + "]")));
    }
}
