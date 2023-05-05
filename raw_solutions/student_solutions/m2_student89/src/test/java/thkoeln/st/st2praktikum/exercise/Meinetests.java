package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Meinetests extends MovementTests {

    @Test
    public void connectionOutOfBoundFailedTest() {

        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertThrows(IllegalArgumentException.class, () -> service.addConnection(room1,new Vector2D(1,10),room2,new Vector2D(22,50)));
        assertThrows(IllegalArgumentException.class, () -> service.addConnection(room1,new Vector2D(5,5),room2,new Vector2D(5,11)));
        assertThrows(RuntimeException.class, () -> service.addConnection(room2,new Vector2D(-3,3),room3,new Vector2D(-2,-1)));

    }


    @Test
    public void BarrierOutOfBoundFailedTest() {
        Barrier barrier_me = new Barrier(new Vector2D(2,3), new Vector2D(2,10));
        Barrier barrier_me2 = new Barrier(new Vector2D(2,3), new Vector2D(2,4));

        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertThrows(IllegalArgumentException.class, () -> service.addBarrier(room1,barrier_me));
        assertThrows(IllegalArgumentException.class, () -> service.addBarrier(room3,barrier_me));
        assertDoesNotThrow( () -> service.addBarrier(room2,barrier_me2));

    }



    @Test
    public void MoveOutofBoundTest() {

        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertDoesNotThrow( () ->  executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[no,50]"),
                new Task("[ea,50]"),
                new Task("[so,50]"),
                new Task("[we,50]"),
        }));

    }
}
