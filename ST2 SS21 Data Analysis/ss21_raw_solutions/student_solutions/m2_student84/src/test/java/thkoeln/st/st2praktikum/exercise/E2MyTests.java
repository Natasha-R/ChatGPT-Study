package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.*;

public class E2MyTests extends MovementTests {
    TidyUpRobotService service;

    @BeforeEach
    public void initEach() {
        service = new TidyUpRobotService();
        createWorld(service);
    }

    @Test
    public void placingBarrierOutOfBoundsNotPossible() {
        assertThrows(RuntimeException.class, () -> service.addBarrier(room1, new Barrier("(10,10)-(10,11)")));
    }

    @Test
    public void transportOnlyPossibleIfOutgoingConnectionAvailable() {
        assertTrue(service.executeCommand(tidyUpRobot1, new Order("[en," + room1 + "]")));
        assertFalse(service.executeCommand(tidyUpRobot1, new Order("[tr," + room2 + "]")));
    }

    @Test
    public void transportOnlyPossibleIfDestinationNotOccupied() {
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]")
        });

        assertTrue(service.executeCommand(tidyUpRobot1, new Order("[tr," + room3 + "]")));

        executeOrders(service, tidyUpRobot2, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]")
        });
        assertFalse(service.executeCommand(tidyUpRobot2, new Order("[tr," + room3 + "]")));

    }
}
