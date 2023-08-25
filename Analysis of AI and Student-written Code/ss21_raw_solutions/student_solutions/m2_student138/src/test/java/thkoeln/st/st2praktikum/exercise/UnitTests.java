package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class UnitTests {

    private UUID cleaner1;
    private UUID cleaner2;
    private UUID space1;
    private UUID space2;
    CleaningDeviceService world;

    @BeforeEach
    private void prepare()
    {
        world = new CleaningDeviceService();
        cleaner1 = world.addCleaningDevice("cleaner1");
        cleaner2 = world.addCleaningDevice("Robo2");
        space1 = world.addSpace(5,6);
        space2 = world.addSpace(3,3);
        world.addConnection(space1, new Vector2D(2,2), space2, new Vector2D(0,1));
    }

    @Test
    public void hitCleaningDevice()
    {
        world.executeCommand(cleaner1, new Command(CommandType.ENTER, space1));
        world.executeCommand(cleaner1, new Command(CommandType.NORTH, 2));
        world.executeCommand(cleaner2, new Command(CommandType.ENTER, space1));
        world.executeCommand(cleaner2, new Command(CommandType.NORTH, 2));

        assertEquals(space1, world.getCleaningDeviceSpaceId(cleaner1));
        assertEquals(new Vector2D(0, 2), world.getVector2D(cleaner1));
        assertEquals(space1, world.getCleaningDeviceSpaceId(cleaner2));
        assertEquals(new Vector2D(0, 1), world.getVector2D(cleaner2));
    }

    @Test
    public void moveOutOfBounds()
    {
        world.executeCommand(cleaner1, new Command(CommandType.ENTER, space1));
        assertEquals(space1, world.getCleaningDeviceSpaceId(cleaner1));
        assertEquals(new Vector2D(0, 0), world.getVector2D(cleaner1));
        world.executeCommand(cleaner1, new Command(CommandType.SOUTH, 2));
        assertEquals(new Vector2D(0, 0), world.getVector2D(cleaner1));
        world.executeCommand(cleaner1, new Command(CommandType.WEST, 2));
        assertEquals(new Vector2D(0, 0), world.getVector2D(cleaner1));
        world.executeCommand(cleaner1, new Command(CommandType.EAST, 8));
        assertEquals(new Vector2D(5, 0), world.getVector2D(cleaner1));
        world.executeCommand(cleaner1, new Command(CommandType.NORTH, 8));
        assertEquals(new Vector2D(5, 4), world.getVector2D(cleaner1));
    }

    @Test
    public void connectionOtOufBounds()
    {
        assertThrows(RuntimeException.class, () -> world.addConnection(space1, new Vector2D(9,5), space2, new Vector2D(4,4)));
    }

    @Test
    public void traverseConnectionWhileOccupied()
    {
        world.executeCommand(cleaner1, new Command(CommandType.ENTER, space1));
        world.executeCommand(cleaner1, new Command(CommandType.NORTH, 2));
        world.executeCommand(cleaner1, new Command(CommandType.EAST, 2));
        world.executeCommand(cleaner1, new Command(CommandType.TRANSPORT, space2));
        assertEquals(space2, world.getCleaningDeviceSpaceId(cleaner1));
        assertEquals(new Vector2D(0, 1), world.getVector2D(cleaner1));

        world.executeCommand(cleaner2, new Command(CommandType.ENTER, space1));
        world.executeCommand(cleaner2, new Command(CommandType.NORTH, 2));
        world.executeCommand(cleaner2, new Command(CommandType.EAST, 2));
        assertFalse(world.executeCommand(cleaner2, new Command(CommandType.TRANSPORT, space2)));
    }

    @Test
    public void PlaceObstacleOutOfBounds()
    {
        assertThrows(RuntimeException.class, () -> world.addObstacle(space1, new Obstacle(new Vector2D(3,3), new Vector2D(10,3))));
    }

    @Test
    public void TransportOnNoOutgoingConnection()
    {
        world.executeCommand(cleaner2, new Command(CommandType.ENTER, space1));
        world.executeCommand(cleaner2, new Command(CommandType.NORTH, 3));
        assertFalse(world.executeCommand(cleaner2, new Command(CommandType.TRANSPORT, space2)));
    }
}
