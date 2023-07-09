package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class E2MovementTests extends MovementTests {
    private TidyUpRobotService service;

    @BeforeEach
    public void beforeTest() {
        service = new TidyUpRobotService();

        room1 = service.addRoom(8,8);
        room2 = service.addRoom(2,2);
        room3 = service.addRoom(5,5);

        tidyUpRobot1 = service.addTidyUpRobot("simba");
        tidyUpRobot2 = service.addTidyUpRobot("nala");

        service.addWall(room1, new Wall("(4,0)-(4,7)"));
        service.addWall(room3, new Wall("(2,2)-(2,3)"));
        service.addWall(room3, new Wall("(4,4)-(4,3)"));

        connection1 = service.addConnection(room1, new Coordinate("(0,0)"), room2, new Coordinate("(0,0)"));
        connection2 = service.addConnection(room2, new Coordinate("(1,1)"), room1, new Coordinate("(6,6)"));
        connection3 = service.addConnection(room3, new Coordinate("(1,2)"), room2, new Coordinate("(0,0)"));
    }
    @AfterEach
    public void afterTest() {
        service = null;
        room1 = null;
        room2 = null;
        room3 = null;

        tidyUpRobot1 = null;
        tidyUpRobot2 = null;

        connection1 = null;
        connection2 = null;
        connection3 = null;
    }

    @Test
    public void hitOtherRobotTest(){
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,3]"),
        });
        executeOrders(service, tidyUpRobot2, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,5]"),
        });

        assertPosition(service, tidyUpRobot1, room1, 3, 0);
        assertPosition(service, tidyUpRobot2, room1, 2, 0);
    }
    @Test
    public void moveOutOfBoundariesTest(){
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[we,3]"),
        });
        assertPosition(service, tidyUpRobot1, room1, 0, 0);
    }
    @Test
    public void connectionDestinationOccupiedTest(){
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[tr," + room2 + "]"),
        });
        executeOrders(service, tidyUpRobot2, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[tr," + room2 + "]"),
        });
        assertPosition(service, tidyUpRobot1, room2, 0, 0);
        assertPosition(service, tidyUpRobot2, room1, 0, 0);
    }
}
