package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E1SelfMadeTests extends MovementTests {
    //@BeforeEach //AfterEach

    @Test
    public void connectionOutOfBoundsTest(){
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertThrows(RuntimeException.class, () -> service.addConnection(room1,new Coordinate(0,0),room2, new Coordinate(9,9)));
        assertThrows(RuntimeException.class, () -> service.addConnection(room2,new Coordinate(1,3),room3, new Coordinate(5,0)));
        assertThrows(RuntimeException.class, () -> service.addConnection(room1,new Coordinate(3,6),room2, new Coordinate(2,6)));

    }
    @Test
    public void traversingToOccupiedDestinationConnectionTest(){
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[no,1]")
        });
        executeTasks(service, tidyUpRobot2, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[ea,1]"),
                new Task("[no,1]")
        });

        assertFalse(service.executeCommand(tidyUpRobot2, new Task("[tr," + room2 + "]")));
    }
    @Test
    public void obstacleOutOfBoundsTest(){
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertThrows(RuntimeException.class, () ->  service.addObstacle(room3, new Obstacle("(0,1)-(0,5)")));
        assertThrows(RuntimeException.class, () ->  service.addObstacle(room1, new Obstacle("(1,1)-(1,7)")));
        assertThrows(RuntimeException.class, () ->  service.addObstacle(room2, new Obstacle("(3,1)-(3,6)")));
    }
}
