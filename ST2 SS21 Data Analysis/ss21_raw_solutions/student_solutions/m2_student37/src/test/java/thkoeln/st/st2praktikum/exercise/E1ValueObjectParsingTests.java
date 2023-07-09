package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import java.util.IllegalFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectParsingTests {

    @Test
    public void moveTaskParsingTest() {
        Task moveTask = new Task("[so,4]");
        assertEquals(TaskType.SOUTH, moveTask.getTaskType());
        assertEquals(Integer.valueOf(4), moveTask.getNumberOfSteps());
    }

    @Test
    public void taskFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> new Task("[soo,4]"));
        assertThrows(RuntimeException.class, () -> new Task("[4, no]"));
        assertThrows(RuntimeException.class, () -> new Task("[[soo,4]]"));
        assertThrows(RuntimeException.class, () -> new Task("(soo,4)"));
    }

    @Test
    public void vector2DParsingTest() {
        Vector2D vector2D = new Vector2D("(2,3)");
        assertEquals(new Vector2D(2, 3), vector2D);
    }

    @Test
    public void vector2DFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> new Vector2D("(2,,3)"));
        assertThrows(RuntimeException.class, () -> new Vector2D("((2,3]"));
        assertThrows(RuntimeException.class, () -> new Vector2D("(1,2,3)"));
        assertThrows(RuntimeException.class, () -> new Vector2D("(1,)"));
    }

    @Test
    public void obstacleParsingTest() {
        Obstacle obstacle = new Obstacle("(1,2)-(1,4)");
        assertEquals(new Vector2D(1, 2), obstacle.getStart());
        assertEquals(new Vector2D(1, 4), obstacle.getEnd());
    }

    @Test
    public void obstacleParsingFailedTest() {
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,2-1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,2),(1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,)-(1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,4)"));
    }


}
