package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveOutOfBoundsTest extends MovementTests {

    TidyUpRobotService service;

    @BeforeEach
    public void createNewWorld(){
        service = new TidyUpRobotService();

        room1 = service.addRoom(2,2);

        tidyUpRobot1 = service.addTidyUpRobot("max");
    }

    @Test
    public void moveOnlyInBoundsTest(){


        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]")
        });

        assertPosition(service, tidyUpRobot1, room1, 0, 0);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[ea,1]")
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 0);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[no,1]")
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 1);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[we,1]")
        });

        assertPosition(service, tidyUpRobot1, room1, 0, 1);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[so,1]")
        });

        assertPosition(service, tidyUpRobot1, room1, 0, 0);

    }

    @Test
    public void moveOutOfBoundsTest(){



        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]")
        });

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[so,1]"),
                new Task("[we,1]"),
        });

        assertPosition(service, tidyUpRobot1, room1, 0, 0);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[ea,1]")
        });
        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[so,1]"),
                new Task("[ea,1]"),
        });
        assertPosition(service, tidyUpRobot1, room1, 1, 0);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[no,1]")
        });
        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[no,1]"),
                new Task("[ea,1]"),
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 1);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[we,1]")
        });
        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[no,1]"),
                new Task("[we,1]"),
        });

        assertPosition(service, tidyUpRobot1, room1, 0, 1);

    }
}

