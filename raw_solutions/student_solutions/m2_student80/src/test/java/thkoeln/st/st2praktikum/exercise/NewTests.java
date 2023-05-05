package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewTests extends MovementTests {

    private  TidyUpRobotService service  = new TidyUpRobotService();
    @BeforeEach
        public void worldInitialize(){
            createWorld(service);
        }





    @Test
    public void placingABarrierOutOfBounce() {

        assertThrows(RuntimeException.class, () -> service.addBarrier(room1, new Barrier("(0,6)-(0,9)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(room2, new Barrier("(6,8)-(6,10)")));
        assertThrows(RuntimeException.class, () -> service.addBarrier(room3, new Barrier("(0,6)-(0,9)")));

    }

    @Test
    public void placingAConnectionOutOfBounce() {


        assertThrows(RuntimeException.class, () -> service.addConnection(room1, new Coordinate("(7,1)"), room2, new Coordinate("(0,1)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(room1, new Coordinate("(1,1)"), room2, new Coordinate("(0,8)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(room2, new Coordinate("(8,0)"), room3, new Coordinate("(2,2)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(room2, new Coordinate("(1,0)"), room3, new Coordinate("(5,2)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(room3, new Coordinate("(2,6)"), room2, new Coordinate("(1,0)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(room3, new Coordinate("(2,2)"), room2, new Coordinate("(1,6)")));

    }

    @Test
    public void hittingAnotherRoboterDuringMovement() {

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[ea,2]"),
                new Task("[no,2]")
        });

        executeTasks(service, tidyUpRobot2, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[no,2]"),
                new Task("[ea,5]")
        });

        assertPosition(service, tidyUpRobot1, room2, 2, 2);
        assertPosition(service, tidyUpRobot2, room2, 1, 2);

    }


}
