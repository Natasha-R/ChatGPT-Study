package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

public class E2Test {
    private TidyUpRobotService tidyUpRobotService;
    private UUID room1, room2, room3;
    private UUID connection1, connection2, connection3;
    private UUID robot1, robot2;

    @BeforeEach
    public void setUp(){
        tidyUpRobotService = new TidyUpRobotService();

        room1 = tidyUpRobotService.addRoom(5,5);
        room2 = tidyUpRobotService.addRoom(10,10);
        room3 = tidyUpRobotService.addRoom(4,4);

        tidyUpRobotService.addObstacle(room1, new Obstacle("(1,3)-(4,3)"));
        tidyUpRobotService.addObstacle(room2, new Obstacle("(4,3)-(4,9)"));
        tidyUpRobotService.addObstacle(room2, new Obstacle("(5,1)-(8,1)"));

        connection1 = tidyUpRobotService.addConnection(room1, new Vector2D("(3,3)"), room2, new Vector2D("(6,5)"));
        connection2 = tidyUpRobotService.addConnection(room2, new Vector2D("(2,1)"), room3, new Vector2D("(2,3)"));
        connection3 = tidyUpRobotService.addConnection(room3, new Vector2D("(0,3)"), room1, new Vector2D("(2,1)"));

        robot1 = tidyUpRobotService.addTidyUpRobot("robot1");
        robot2 = tidyUpRobotService.addTidyUpRobot("robot2");
    }

    @AfterEach
    public void tearDown(){
        tidyUpRobotService = null;
    }

    private void executeCommand(UUID robotId, Command[] commands){
        for(Command command : commands){
            tidyUpRobotService.executeCommand(robotId, command);
        }
    }

    @Test
    public void bumpIntoAnotherRobotTest(){
        Command[] commands1 = new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[no,6]"),
                new Command("[ea,2]")
        };

        Command[] commands2 = new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[ea,3]"),
                new Command("[no,8]"),
                new Command("[we,1]"),
                new Command("[so,6]")
        };

        executeCommand(robot1, commands1);
        executeCommand(robot2, commands2);

        assertEquals(room2, tidyUpRobotService.getTidyUpRobotRoomId(robot1));
        assertEquals(new Vector2D(2,6), tidyUpRobotService.getVector2D(robot1));

        assertEquals(room2, tidyUpRobotService.getTidyUpRobotRoomId(robot2));
        assertEquals(new Vector2D(2,7), tidyUpRobotService.getVector2D(robot2));
    }

    @Test
    public void destinationTransportIsOccupiedTest(){
        Command[] commands1 = new Command[]{
                new Command("[en," + room3 + "]"),
                new Command("[ea,1]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
                new Command("[tr," + room1 + "]")
        };

        Command[] commands2 = new Command[]{
                new Command("[en," + room3 + "]"),
                new Command("[no,3]"),
        };

        executeCommand(robot1, commands1);
        executeCommand(robot2, commands2);

        assertEquals(room1, tidyUpRobotService.getTidyUpRobotRoomId(robot1));
        assertEquals(new Vector2D(2,1), tidyUpRobotService.getVector2D(robot1));

        assertFalse(tidyUpRobotService.executeCommand(robot2, new Command("[tr," + room1 + "]")));
        tidyUpRobotService.executeCommand(robot2, new Command("[tr," + room1 + "]"));
        assertEquals(room3, tidyUpRobotService.getTidyUpRobotRoomId(robot2));
        assertEquals(new Vector2D(0,3), tidyUpRobotService.getVector2D(robot2));
    }

    @Test
    public void putObstacleOutsideAreaTest(){
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addObstacle(room1, new Obstacle("(3,4)-(3,10)")));
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addObstacle(room1, new Obstacle("(4,1)-(7,1)")));
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addObstacle(room2, new Obstacle("(5,9)-(5,15)")));
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addObstacle(room2, new Obstacle("(7,6)-(12,6)")));
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addObstacle(room3, new Obstacle("(1,2)-(1,7)")));
        assertThrows(RuntimeException.class, () -> tidyUpRobotService.addObstacle(room3, new Obstacle("(5,1)-(8,1)")));
    }

}
