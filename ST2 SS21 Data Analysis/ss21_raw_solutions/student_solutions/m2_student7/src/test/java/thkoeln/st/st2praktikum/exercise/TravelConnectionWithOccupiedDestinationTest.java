package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TravelConnectionWithOccupiedDestinationTest extends MovementTests {

    TidyUpRobotService service;




    @BeforeEach
    public void createNewWorld(){
        service = new TidyUpRobotService();

        room1 = service.addRoom(1,2);
        room2 = service.addRoom(1,2);

        tidyUpRobot1 = service.addTidyUpRobot("max");
        tidyUpRobot2 = service.addTidyUpRobot("steve");

        service.addConnection(room1, new Vector2D("(1,0)"), room2, new Vector2D("(1,0)"));
        service.addConnection(room2, new Vector2D("(1,0)"), room1, new Vector2D("(1,0)"));
    }


    @Test
    public void transportToValidDestinationAndBackTest(){


        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[ea,1]")
        });
        executeTasks(service, tidyUpRobot2, new Task[]{
                new Task("[en," + room2 + "]"),
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 0);
        assertPosition(service, tidyUpRobot2, room2, 0, 0);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[tr," + room2 + "]"),
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 0);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[tr," + room1 + "]"),
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 0);
    }

    @Test
    public void transportToOccupiedDestinationTest(){


        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[ea,1]")
        });
        executeTasks(service, tidyUpRobot2, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[ea,1]")
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 0);
        assertPosition(service, tidyUpRobot2, room2, 1, 0);


        assertFalse(service.executeCommand(tidyUpRobot1, new Task("[tr," + room2 + "]")));

        assertFalse(service.executeCommand(tidyUpRobot2, new Task("[tr," + room1 + "]")));

        assertPosition(service, tidyUpRobot1, room1, 1, 0);
        assertPosition(service, tidyUpRobot2, room2, 1, 0);

    }
}
