package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exceptions.OutOfBoundsException;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E2UnitTests {

    private TidyUpRobotService service;
    private UUID room1;
    private UUID room2;
    private UUID robot1;
    private UUID robot2;

    @BeforeEach
    public void setUp(){
        service = new TidyUpRobotService();
        room1 = service.addRoom(6,6);
        room2 = service.addRoom(10,10);
        robot1 = service.addTidyUpRobot("Robert");
        robot2 = service.addTidyUpRobot("Robby");
        service.addConnection(room1, new Point("(0,0)"), room2, new Point("(4,0)"));
    }

    @Test
    public void bumpIntoAnotherRobotTest(){
        //given
            service.executeCommand(robot1, new Task("[en," + room1 + "]"));
            service.executeCommand(robot1, new Task("[ea,4]"));
            service.executeCommand(robot2, new Task("[en," + room1 + "]"));
        //when
            service.executeCommand(robot2, new Task("[ea,4]"));
        //then
            assertEquals("(3,0)",service.getPoint(robot2).toString());
    }

    @Test
    public void moveOutOfBoundsTest(){
        //given
            service.executeCommand(robot1, new Task("[en," + room1 + "]"));
        //when
            service.executeCommand(robot1, new Task("[no,20]"));
        //then
            assertEquals("(0,5)", service.getPoint(robot1).toString());
    }

    @Test
    public void placeConnectionOutOfBoundsTest(){
            assertThrows(OutOfBoundsException.class, () -> service.addConnection(room1, new Point("(10,4)"), room2, new Point("(1,1)")));
    }

    @Test
    public void connectionIsBlockedByRobotTest(){
        //given
            service.executeCommand(robot1, new Task("[en," + room1 + "]"));
            service.executeCommand(robot1, new Task("[tr," + room2 + "]"));
            service.executeCommand(robot2, new Task("[en," + room1 + "]"));
        //when
            service.executeCommand(robot2, new Task("[tr," + room2 + "]"));
        //then
            assertEquals(room1, service.getTidyUpRobotRoomId(robot2));
    }

    @Test
    public void transportWithoutConnectionTest(){
        //given
            service.executeCommand(robot1, new Task("[en," + room1 + "]"));
            service.executeCommand(robot1, new Task("[ea,4]"));
        //when
            service.executeCommand(robot1, new Task("[tr," + room2 + "]"));
        //then
            assertEquals(room1, service.getTidyUpRobotRoomId(robot1));
    }

    @Test
    public void placeWallOutOfBoundsTest(){
        assertThrows(OutOfBoundsException.class, () -> service.addWall(room1, new Wall(new Point("(100,4)"), new Point("(1,4)"))));
    }
}
