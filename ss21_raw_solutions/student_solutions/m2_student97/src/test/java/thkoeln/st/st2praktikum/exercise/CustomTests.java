package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomTests extends MovementTests {


    @Test
    public void moveOutOfBoundsTest () {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new Command[] { 
            new Command("[en," + room1 + "]"), 
            new Command("[so,2]")});

        assertPosition(service, tidyUpRobot1, room1, 0, 0);
    }


    @Test
    public void transportWithoutConnection () {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertTrue(service.executeCommand(tidyUpRobot1, new Command("[en," + room1 + "]")));
        assertFalse(service.executeCommand(tidyUpRobot1, new Command("[tr," + room2 + "]")));
    }


    @Test
    public void createBarrierOutOfBounds () {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertThrows(RuntimeException.class, () -> service.addBarrier(room3, new Barrier("(5,5)-(6,5)")));
    }
}
