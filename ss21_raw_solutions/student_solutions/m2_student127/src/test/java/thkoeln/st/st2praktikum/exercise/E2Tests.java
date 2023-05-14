package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import java.util.UUID;

public class E2Tests {

    private TidyUpRobotService testLab = null;
    private UUID roomId0 = null;
    private UUID roomId1 = null;
    private UUID robotId0 = null;
    private UUID robotId1 = null;
    // Should the Points be initialized here, in setUp() or in transportWithoutConnectionTest()?
    private final Point sourcePoint = new Point(2,3);
    private final Point destinationPoint = new Point(4, 5);

    @BeforeEach
    public void setUp() {
        testLab = new TidyUpRobotService();
        roomId0 = testLab.addRoom(5, 5);
        roomId1 = testLab.addRoom(7, 7);
        robotId0 = testLab.addTidyUpRobot("Wall-E");
        robotId1 = testLab.addTidyUpRobot("EVA");
        testLab.addConnection(roomId0, sourcePoint, roomId1, destinationPoint);
    }

    @AfterEach
    public void tearDown() {
        testLab = null;
        roomId0 = null;
        roomId1 = null;
        robotId0 = null;
        robotId1 = null;
    }

    // 1. Hitting another tidyUpRobot during a Move-Command has to interrupt the current movement.
    @Test
    public void stoppedByAnotherRobotTest() {
        testLab.executeCommand(robotId0, new Task("[en," + roomId0 + "]"));
        testLab.executeCommand(robotId0, new Task("[ea,3]"));

        testLab.executeCommand(robotId1, new Task("[en," + roomId0 + "]"));
        testLab.executeCommand(robotId1, new Task("[ea,5]"));

        assertEquals(new Point(3, 0), testLab.getPoint(robotId0));
        assertEquals(new Point(2, 0), testLab.getPoint(robotId1));
    }

    // 2. Trying to move out of bounds must not be possible.
    @Test
    public void moveOutOfBoundsTest() {
        testLab.executeCommand(robotId0, new Task("[en," + roomId0 + "]"));
        testLab.executeCommand(robotId0, new Task("[no,10]"));
        assertEquals(new Point(0,5),testLab.getPoint(robotId0));

        testLab.executeCommand(robotId0, new Task("[ea,10]"));
        assertEquals(new Point(5,5),testLab.getPoint(robotId0));

        testLab.executeCommand(robotId0, new Task("[so,10]"));
        assertEquals(new Point(5,0),testLab.getPoint(robotId0));

        testLab.executeCommand(robotId0, new Task("[we,10]"));
        assertEquals(new Point(0,0),testLab.getPoint(robotId0));
    }

    // 5. Using the TR-command must not be possible if there is no outgoing connection on the current position.
    @Test
    public void transportWithoutConnectionTest() {
        testLab.executeCommand(robotId0, new Task("[en," + roomId0 + "]"));
        assertNotEquals(sourcePoint,testLab.getPoint(robotId0));
        assertFalse(testLab.executeCommand(robotId0, new Task("[tr," + roomId1 + "]")));

        testLab.executeCommand(robotId0, new Task("[no,3]"));
        assertNotEquals(sourcePoint,testLab.getPoint(robotId0));
        assertFalse(testLab.executeCommand(robotId0, new Task("[tr," + roomId1 + "]")));

        testLab.executeCommand(robotId0, new Task("[ea,2]"));
        assertEquals(sourcePoint,testLab.getPoint(robotId0));
        assertTrue(testLab.executeCommand(robotId0, new Task("[tr," + roomId1 + "]")));
    }
}
