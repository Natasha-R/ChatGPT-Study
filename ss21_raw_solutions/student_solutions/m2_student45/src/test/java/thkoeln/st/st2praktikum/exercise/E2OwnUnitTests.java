package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class E2OwnUnitTests extends MovementTests {
    private TidyUpRobotService service;

    @BeforeEach
    public void initializeService() {
        service = new TidyUpRobotService();
        createWorld(service);
    }

    @Test
    public void placingConnectionOutOfBoundTest() {
        assertThrows(InvalidCoordinateException.class, () ->
                        service.addConnection(
                                room1, new Coordinate("(1,2)"),
                                room2, new Coordinate("(0,6)")
                        )
        );

        assertThrows(InvalidCoordinateException.class, () ->
                service.addConnection(
                        room1, new Coordinate("(1,-1)"),
                        room2, new Coordinate("(0,1)")
                )
        );
    }

    @Test
    public void placingWallOutOfBoundTest() {
        assertThrows(InvalidCoordinateException.class, () ->
                service.addWall(room1, new Wall("(1,2)-(1,7)"))
        );

        assertThrows(InvalidCoordinateException.class, () ->
                service.addWall(room1, new Wall("(-1,0)-(0,0)"))
        );

        assertThrows(InvalidCoordinateException.class, () ->
                service.addWall(room1, new Wall("(2,-3)-(0,3)"))
        );
    }

    @Test
    public void traversingOccupiedConnectionTest() {
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,1]")
        });

        executeOrders(service, tidyUpRobot2, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[no,1]"),
                new Order("[tr," + room1 + "]"),
        });

        assertPosition(service, tidyUpRobot2, room2, 0, 1);
    }
}
