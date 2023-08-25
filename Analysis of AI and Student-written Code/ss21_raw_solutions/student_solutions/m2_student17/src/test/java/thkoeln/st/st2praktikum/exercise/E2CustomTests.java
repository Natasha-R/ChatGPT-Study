package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.BitPaw.*;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2CustomTests
{
    private TidyUpRobotService service;
    private UUID roomIDAlpha;

    @BeforeEach
    private void createRoom()
    {
        service = new TidyUpRobotService();
        roomIDAlpha = service.addRoom(2,2);
    }

    private void moveAndCheckPosition(UUID robotUUID, Order command, Vector2D position)
    {
        service.executeCommand(robotUUID, command);

        assertEquals(service.getVector2D(robotUUID), position);
    }

    @Test
    public void moveOutOfBounceTest()
    {
        // Trying to move out of bounds must not be possible

        final UUID robotUUID = service.addTidyUpRobot("Boi");
        final Order setRobot = new Order(OrderType.ENTER, roomIDAlpha);
        final Order moveDown = new Order("[so,1000]");
        final Order moveUp = new Order("[no,1]");
        final Order moveLeft = new Order("[we,1000]");

        moveAndCheckPosition(robotUUID, setRobot,  new Vector2D(0, 0));
        moveAndCheckPosition(robotUUID, moveDown,  new Vector2D(0, 0));
        moveAndCheckPosition(robotUUID, moveUp,  new Vector2D(0, 1));
        moveAndCheckPosition(robotUUID, moveLeft,  new Vector2D(0, 1));
    }

    @Test
    public void placeConnectionOutOfBounceTest()
    {
        // Placing a connection out of bounds must not be possible

        final UUID roomIDBeta = service.addRoom(5,5);

        final Vector2D location = new Vector2D(2,2);
        final Vector2D targetLocation = new Vector2D(1000,1);

        assertThrows(InvalidPositionException.class, () -> service.addConnection(roomIDAlpha, location, roomIDBeta, targetLocation));
    }

    @Test
    public void placeBarrierOutOfBounceTest()
    {
        // Placing a barrier out of bounds must not be possible

        final Barrier barrier = new Barrier("(0,1)-(3,1)");

        assertThrows(InvalidPositionException.class, () -> service.addBarrier(roomIDAlpha, barrier));
    }
}
