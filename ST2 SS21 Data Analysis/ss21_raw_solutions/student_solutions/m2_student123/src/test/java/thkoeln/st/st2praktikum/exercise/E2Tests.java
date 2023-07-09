package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class E2Tests extends MovementTests {
    TidyUpRobotService service;
    @BeforeEach
    public void initializeTests(){
        service = new TidyUpRobotService();
        createWorld(service);
    }

    @Test
    public void moveRobotOutOfBoundsTest(){
        UUID room4 = service.addRoom(6,6);

        service.executeCommand(tidyUpRobot1,new Task(TaskType.ENTER,room4));
        service.executeCommand(tidyUpRobot1,new Task(TaskType.NORTH,2));
        assertPosition(service,tidyUpRobot1,room4,0,2);
        service.executeCommand(tidyUpRobot1,new Task(TaskType.SOUTH,4));
        assertPosition(service,tidyUpRobot1,room4,0,0);
        service.executeCommand(tidyUpRobot1,new Task(TaskType.EAST,7));
        assertPosition(service,tidyUpRobot1,room4,5,0);
    }

    @Test
    public void PlaceConnectionOutOfBoundsTest() {
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(7,7),room2,new Vector2D(1,1)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(8,8),room2,new Vector2D(1,1)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(2,2),room2,new Vector2D(6,6)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(2,2),room2,new Vector2D(7,7)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(-1,-1),room2,new Vector2D(1,1)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(-2,-2),room2,new Vector2D(1,1)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(2,2),room2,new Vector2D(6,6)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room1,new Vector2D(2,2),room2,new Vector2D(7,7)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room2,new Vector2D(6,6),room3,new Vector2D(4,4)));
        assertThrows(IllegalArgumentException.class,()->service.addConnection(room2,new Vector2D(7,7),room3,new Vector2D(5,5)));

    }

    @Test
    public void useTransitionCommandWithoutConnection(){
        service.executeCommand(tidyUpRobot1,new Task(TaskType.ENTER,room3));
        assertPosition(service,tidyUpRobot1,room3,0,0);
        assertFalse(service.executeCommand(tidyUpRobot1,new Task(TaskType.TRANSPORT,room2)));
        assertPosition(service,tidyUpRobot1,room3,0,0);
    }

    @Test
    public void placeBarrierOutOfBounds(){
        UUID room4 = service.addRoom(6,6);
        assertThrows(ArrayIndexOutOfBoundsException.class,()->service.addBarrier(room4,new Barrier("(1,2)-(1,14)")));
    }
}
