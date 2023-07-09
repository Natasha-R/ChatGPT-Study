package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Exception.NegativePointCoordinateException;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E2Tests extends MovementTests {
    CleaningDeviceService service;

    @BeforeEach
    public void createWorld(){
        service = new CleaningDeviceService();
        createWorld(service);
    }

    @Test
    public void connectionNotOnFieldNoPlacement(){
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Point("(-3,1)"),space3, new Point("(7,19)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Point("(30,1)"),space3, new Point("(11,1)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Point("(3,-1)"),space2, new Point("(12,-3)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(space1,new Point("(3,20)"),space2, new Point("(0,1)")));

    }

    @Test
    public void moveCleaningDeviceOutOfBounds() {
        assertThrows(RuntimeException.class, () -> service.executeCommand(cleaningDevice1, new Task(TaskType.EAST,20)));
    }

    @Test
    public void obstacleNotOnFieldNoPlacement(){
        assertThrows(RuntimeException.class, () -> service.addObstacle(space1, new Obstacle("(-17,3)-(7,3)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(space1, new Obstacle("(17,3)-(17,6)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(space1, new Obstacle("(3,-4)-(3,7)")));
        assertThrows(RuntimeException.class, () -> service.addObstacle(space1, new Obstacle("(1,30)-(12,30)")));

    }
}