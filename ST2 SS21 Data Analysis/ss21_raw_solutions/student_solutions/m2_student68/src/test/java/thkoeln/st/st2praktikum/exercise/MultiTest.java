package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

public class MultiTest {

    protected TidyUpRobotService service;
    protected UUID room1;
    protected UUID room2;
    protected UUID tidyUpRobot1;
    protected UUID tidyUpRobot2;
    protected UUID connection1;
    protected UUID connection2;

    @BeforeEach
    public void createWorld() {
        service = new TidyUpRobotService();
        room1 = service.addRoom(4,5);
        room2 = service.addRoom(3,3);

        tidyUpRobot1 = service.addTidyUpRobot("Charlie");
        tidyUpRobot2 = service.addTidyUpRobot("Snoopy");

        service.addBarrier(room1, new Barrier("(0,3)-(3,3)"));
        service.addBarrier(room1, new Barrier("(2,1)-(4,1)"));
        service.addBarrier(room2, new Barrier("(1,1)-(1,3)"));

        connection1 = service.addConnection(room1, new Coordinate("(2,2)"), room2, new Coordinate("(0,2)"));
        connection2 = service.addConnection(room2, new Coordinate("(0,0)"), room1, new Coordinate("(4,0)"));
    }

    @Test
    public void BlockByAnotherRobot() {
        service.executeCommand(tidyUpRobot1,new Task("[en,"+room1+"]"));
        service.executeCommand(tidyUpRobot1,new Task("[ea,1]"));
        service.executeCommand(tidyUpRobot2,new Task("[en,"+room1+"]"));
        service.executeCommand(tidyUpRobot2,new Task("[ea,5]"));

        assertEquals(room1,service.getTidyUpRobotRoomId(tidyUpRobot2));
        assertEquals(new Coordinate(0,0),service.map.getRobotById(tidyUpRobot2).getCoordinate());

        service.executeCommand(tidyUpRobot1,new Task("[ea,3]"));
        service.executeCommand(tidyUpRobot2,new Task("[ea,10]"));

        assertEquals(room1,service.getTidyUpRobotRoomId(tidyUpRobot2));
        assertEquals(new Coordinate(3,0),service.map.getRobotById(tidyUpRobot2).getCoordinate());
    }


    @Test
    public void BlockByRoomBordersAndBarries() {
        service.executeCommand(tidyUpRobot1,new Task("[en,"+room1+"]"));
        service.executeCommand(tidyUpRobot1,new Task("[no,100]"));
        service.executeCommand(tidyUpRobot1,new Task("[ea,100]"));

        assertEquals(room1,service.getTidyUpRobotRoomId(tidyUpRobot1));
        assertEquals(new Coordinate(4,2),service.map.getRobotById(tidyUpRobot1).getCoordinate());

        service.executeCommand(tidyUpRobot1,new Task("[no,100]"));
        service.executeCommand(tidyUpRobot1,new Task("[we,100]"));

        assertEquals(room1,service.getTidyUpRobotRoomId(tidyUpRobot1));
        assertEquals(new Coordinate(0,3),service.map.getRobotById(tidyUpRobot1).getCoordinate());
    }


    @Test
    public void TransportToAnotherRoom() {
        service.executeCommand(tidyUpRobot1,new Task("[en,"+room1+"]"));
        service.executeCommand(tidyUpRobot1,new Task("[no,2]"));
        service.executeCommand(tidyUpRobot1,new Task("[ea,2]"));

        service.executeCommand(tidyUpRobot1,new Task("[tr,"+room2+"]"));
        assertEquals(room2,service.getTidyUpRobotRoomId(tidyUpRobot1));
        assertEquals(new Coordinate(0,2),service.map.getRobotById(tidyUpRobot1).getCoordinate());

        assertEquals(false,service.executeCommand(tidyUpRobot1,new Task("[tr,"+room1+"]")));
        assertEquals(room2,service.getTidyUpRobotRoomId(tidyUpRobot1));

        service.executeCommand(tidyUpRobot1,new Task("[so,2]"));
        service.executeCommand(tidyUpRobot1,new Task("[tr,"+room1+"]"));
        assertEquals(room1,service.getTidyUpRobotRoomId(tidyUpRobot1));
        assertEquals(new Coordinate(4,0),service.map.getRobotById(tidyUpRobot1).getCoordinate());
    }


    @AfterEach
    public void reset() {
    System.out.println("BOOOOOOOOOOOOMMMMMMM !!!!");
    System.out.println("Snoopy destroyed the robot world by dropping atomic bomb from airplane.");
    }



}
