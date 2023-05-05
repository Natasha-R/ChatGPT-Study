package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectParsingTests {

    @Test
    public void moveTaskParsingTest() {
        Task moveTask = Task.fromString("[so,4]");
        assertEquals(TaskType.SOUTH, moveTask.getTaskType());
        assertEquals(Integer.valueOf(4), moveTask.getNumberOfSteps());
    }

    @Test
    public void taskFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> Task.fromString("[soo,4]"));
        assertThrows(RuntimeException.class, () -> Task.fromString("[4, no]"));
        assertThrows(RuntimeException.class, () -> Task.fromString("[[soo,4]]"));
        assertThrows(RuntimeException.class, () -> Task.fromString("(soo,4)"));
    }

    @Test
    public void vector2DParsingTest() {
        Vector2D vector2D = Vector2D.fromString("(2,3)");
        assertEquals(new Vector2D(2, 3), vector2D);
    }

    @Test
    public void vector2DFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> Vector2D.fromString("(2,,3)"));
        assertThrows(RuntimeException.class, () -> Vector2D.fromString("((2,3]"));
        assertThrows(RuntimeException.class, () -> Vector2D.fromString("(1,2,3)"));
        assertThrows(RuntimeException.class, () -> Vector2D.fromString("(1,)"));
    }

    @Test
    public void barrierParsingTest() {
        Barrier barrier = Barrier.fromString("(1,2)-(1,4)");
        assertEquals(new Vector2D(1, 2), barrier.getStart());
        assertEquals(new Vector2D(1, 4), barrier.getEnd());
    }

    @Test
    public void barrierParsingFailedTest() {
        assertThrows(RuntimeException.class, () -> Barrier.fromString("(1,2-1,4)"));
        assertThrows(RuntimeException.class, () -> Barrier.fromString("(1,2),(1,4)"));
        assertThrows(RuntimeException.class, () -> Barrier.fromString("(1,)-(1,4)"));
        assertThrows(RuntimeException.class, () -> Barrier.fromString("(1,4)"));
    }


}
