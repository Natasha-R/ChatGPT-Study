package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.core.MyTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class E2Tests extends MyTests {

    protected TidyUpRobotService service = new TidyUpRobotService();

    @BeforeEach
    public void setUp(){
        createWorld(service);
    }

    @Test
    public void createConnectionOutOfBoundaries() {
        assertThrows(RuntimeException.class, ()->service.addConnection(room1, new Point("(7,7)"), room2, new Point("(3,3)")));
        assertThrows(RuntimeException.class, ()-> service.addConnection(room3, new Point("(1,1)"),room1,new Point("(-3,-3)")));
    }

    @Test
    public void teleportWithoutConnection(){
       executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room2 + "]")
        });
       assertPosition(service,tidyUpRobot1,room2,0,0);
       assertFalse(service.executeCommand(tidyUpRobot1, new Task("[tr,"+room3+"]")));
    }

    @Test
    public void moveAgainstOutOfBoundaries(){

        executeTasks(service,tidyUpRobot1,new Task[]{
                new Task("[en," + room4 + "]"),
                new Task("[no,30]"),
        });

        assertPosition(service, tidyUpRobot1, room4, 0, 19);

        executeTasks(service,tidyUpRobot1,new Task[]{
                new Task("[ea,35]"),
        });

        assertPosition(service, tidyUpRobot1, room4, 19, 19);

        executeTasks(service,tidyUpRobot1,new Task[]{
                new Task("[so,50]"),
        });

        assertPosition(service, tidyUpRobot1, room4, 19, 0);

        executeTasks(service,tidyUpRobot1,new Task[]{
                new Task("[we,100]"),
        });

        assertPosition(service, tidyUpRobot1, room4, 0, 0);

    }
}
