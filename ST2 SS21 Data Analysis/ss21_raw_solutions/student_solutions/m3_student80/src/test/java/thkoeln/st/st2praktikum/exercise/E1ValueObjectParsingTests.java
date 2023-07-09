package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void coordinateParsingTest() {
        Coordinate coordinate = new Coordinate("(2,3)");
        assertEquals(new Coordinate(2, 3), coordinate);
    }

    @Test
    public void coordinateFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> new Coordinate("(2,,3)"));
        assertThrows(RuntimeException.class, () -> new Coordinate("((2,3]"));
        assertThrows(RuntimeException.class, () -> new Coordinate("(1,2,3)"));
        assertThrows(RuntimeException.class, () -> new Coordinate("(1,)"));
    }

    @Test
    public void barrierParsingTest() {
        Barrier barrier = new Barrier("(1,2)-(1,4)");
        assertEquals(new Coordinate(1, 2), barrier.getStart());
        assertEquals(new Coordinate(1, 4), barrier.getEnd());
    }

    @Test
    public void barrierParsingFailedTest() {
        assertThrows(RuntimeException.class, () -> new Barrier("(1,2-1,4)"));
        assertThrows(RuntimeException.class, () -> new Barrier("(1,2),(1,4)"));
        assertThrows(RuntimeException.class, () -> new Barrier("(1,)-(1,4)"));
        assertThrows(RuntimeException.class, () -> new Barrier("(1,4)"));
    }


}
