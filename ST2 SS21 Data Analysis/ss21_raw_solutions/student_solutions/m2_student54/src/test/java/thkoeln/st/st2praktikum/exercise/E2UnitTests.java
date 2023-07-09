package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.*;

public class E2UnitTests extends MovementTests
{
    private TidyUpRobotService tidyUpRobotService;

    @BeforeEach
    public void initializeServiceAndWorld()
    {
        tidyUpRobotService = new TidyUpRobotService();
        createWorld(tidyUpRobotService);
    }

    @Test
    public void placingConnectionOutOfBoundsTest()
    {
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addConnection(room1, new Vector2D(8, 3), room3, new Vector2D(1,2)));
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addConnection(room1, new Vector2D(4, 4), room3, new Vector2D(1,3)));
    }

    @Test
    public void noOutgoingConnectionTest()
    {
        executeTasks(tidyUpRobotService, tidyUpRobot2, new Task[]{
                new Task("[en," + room3 + "]"),
                new Task("[no,2]"),
                new Task("[ea,2]")
        });

        assertTrue(tidyUpRobotService.executeCommand(tidyUpRobot2, new Task("[tr," + room2 + "]")));

        executeTasks(tidyUpRobotService, tidyUpRobot2, new Task[]{
                new Task("[we,1]")
        });
        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot2, new Task("[tr," + room3 + "]")));
    }

    @Test
    public void traversingToOccupiedDestinationTest()
    {
        executeTasks(tidyUpRobotService, tidyUpRobot1, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[no,1]"),
        });

        assertPosition(tidyUpRobotService, tidyUpRobot1, room2, 0, 1);

        executeTasks(tidyUpRobotService, tidyUpRobot2, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[no,1]"),
                new Task("[ea,1]")
        });

        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot2, new Task("[tr," + room2 + "]")));
    }
}
