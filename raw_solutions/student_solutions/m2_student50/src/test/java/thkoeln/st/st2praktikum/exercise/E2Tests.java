package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2Tests {

    TidyUpRobotService service = new  TidyUpRobotService();

    protected UUID room1;
    protected UUID room2;
    protected UUID room3;

    protected UUID tidyUpRobot1;
    protected UUID tidyUpRobot2;

    protected UUID connection1;

    protected void executeOrders(TidyUpRobotService service, UUID tidyUpRobot, Order[] orders) {
        for (Order order : orders) {
            service.executeCommand(tidyUpRobot, order);
        }
    }

    @BeforeEach
    public void setUp(){
        room1 = service.addRoom(5,5);
        room2 = service.addRoom(4,4);
        room3 = service.addRoom(3,3);

        tidyUpRobot1 = service.addTidyUpRobot("woozoo");
        tidyUpRobot2 = service.addTidyUpRobot("hazen");

        connection1 = service.addConnection(room1, new Coordinate("(3,0)"), room2, new Coordinate("(1,2)"));
    }

    @Test
    public void placingConnectionOutOfBoundsTest(){

        assertThrows(RuntimeException.class, () -> service.addConnection(room1,new Coordinate(3,3),room2, new Coordinate(1,5)));
        assertThrows(RuntimeException.class, () -> service.addConnection(room3,new Coordinate(2,1),room1, new Coordinate(6,1)));
        assertThrows(RuntimeException.class, () -> service.addConnection(room3,new Coordinate(0,2),room2, new Coordinate(3,5)));
        assertThrows(RuntimeException.class, () -> service.addConnection(room2,new Coordinate(1,3),room2, new Coordinate(5,3)));

    }

    @Test
    public void placingWallOutOfBoundsTest(){

        assertThrows(RuntimeException.class, () -> service.addWall(room1, new Wall("(1,4)-(6,3)")));
        assertThrows(RuntimeException.class, () -> service.addWall(room2, new Wall("(1,5)-(1,4)")));
        assertThrows(RuntimeException.class, () -> service.addWall(room3, new Wall("(3,1)-(3,3)")));
        assertThrows(RuntimeException.class, () -> service.addWall(room2, new Wall("(3,1)-(4,3)")));

    }


    @Test
    public void moveOutOfBoundsTest() {
        TidyUpRobotService service = new TidyUpRobotService();

        assertThrows(RuntimeException.class, () -> executeOrders(service, tidyUpRobot1,new Order[]{
                new Order("[en," + room3 + "]"),
                new Order("[no,2]"),
                new Order("[ea,1]"),
                new Order("[so,5]")
        }));

        assertThrows(RuntimeException.class, () -> executeOrders(service, tidyUpRobot2,new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,3]"),
                new Order("[we,1]"),
                new Order("[no,6]")
        }));

        assertThrows(RuntimeException.class, () -> executeOrders(service, tidyUpRobot1,new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[no,1]"),
                new Order("[ea,3]"),
                new Order("[so,1]"),
                new Order("[no,4]")
        }));

    }

}
