package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.*;

public class E2CustomTests extends MovementTests
{
    public TidyUpRobotService service;

    @BeforeEach
    public void initializeWorld()
    {
        this.service = new TidyUpRobotService();
        createWorld(service);
    }

    @Test
    public void moveOutOfBoundsTest()
    {
        assertTrue(service.executeCommand(tidyUpRobot1, new Order("[en," + room1 + "]")));
        assertTrue(service.executeCommand(tidyUpRobot1, new Order("[we,5]")));
        assertPosition(service, tidyUpRobot1, room1, 0, 0);
    }

    @Test
    public void placeConnectionOutOfBoundsTest()
    {
        assertThrows(ConnectionOutOfBoundsException.class, () -> this.service.addConnection(room1, new Coordinate(75, 104), room2, new Coordinate(102, 120)));
    }

    @Test
    public void placeBarrierOutOfBoundsTest()
    {
        assertThrows(BarrierOutOfBoundsException.class, () -> this.service.addBarrier(room1, new Barrier(new Coordinate(120, 150), new Coordinate(120, 200))));
    }
}
