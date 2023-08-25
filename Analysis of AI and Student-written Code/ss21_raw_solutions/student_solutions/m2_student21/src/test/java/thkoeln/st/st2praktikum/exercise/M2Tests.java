package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class M2Tests extends MovementTests {

    private TidyUpRobotService service;

    @BeforeEach
    public void init(){
        service = new TidyUpRobotService();
        createWorld(service);
    }

    // Unit Test 4
    @Test
    public void traversingToOccupiedPosition(){
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[no,1]"),
                new Order("[ea,1]"),
        });

        executeOrders(service, tidyUpRobot2, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[no,1]"),
        });

        assertFalse(service.executeCommand(tidyUpRobot1, new Order("[tr," + room2 + "]")));
    }

    // Unit Test 5
    @Test
    public void traversingWithoutOutgoingConnection(){
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,1]"),
        });

        assertFalse(service.executeCommand(tidyUpRobot1, new Order("[tr," + room2 + "]")));

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[no,1]"),
        });

        assertFalse(service.executeCommand(tidyUpRobot1, new Order("[tr," + room3 + "]")));
    }

    // Unit Test 2
    @Test
    public void moveOutOfBoundsTest(){

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room3 + "]"),
                new Order("[ea,10]"),
                new Order("[we,10]"),

                new Order("[no,1]"),
                new Order("[ea,10]"),
                new Order("[we,10]"),

                new Order("[no,1]"),
                new Order("[ea,10]"),
                new Order("[we,10]"),

                new Order("[no,10]"),
                new Order("[so,10]"),
                new Order("[ea,1]"),

                new Order("[no,10]"),
                new Order("[so,10]"),
                new Order("[ea,1]"),

                new Order("[no,10]"),
                new Order("[so,10]"),
                new Order("[ea,1]"),
        });

        assertPosition(service, tidyUpRobot1, room3, 2, 0);
    }

}
