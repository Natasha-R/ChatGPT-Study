package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class meTests extends MovementTests {
    @Test
    public void moveTwoTidyUpRobotsAtOnceTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,5]")
        });

        executeCommands(service, tidyUpRobot2, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 2);
        assertPosition(service, tidyUpRobot2, room1, 2, 5);
    }
}
