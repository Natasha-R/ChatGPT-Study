package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectValidationTests {

    @Test
    public void ensureBarrierVector2DsAreOrderedTest() {
        Barrier barrier1 = new Barrier(new Vector2D(2, 1 ), new Vector2D(4, 1 ));
        assertEquals(new Vector2D(2, 1 ), barrier1.getStart());
        assertEquals(new Vector2D(4, 1 ), barrier1.getEnd());

        Barrier barrier2 = new Barrier(new Vector2D(4, 1 ), new Vector2D(2, 1 ));
        assertEquals(new Vector2D(2, 1 ), barrier2.getStart());
        assertEquals(new Vector2D(4, 1 ), barrier2.getEnd());

        Barrier barrier3 = new Barrier(new Vector2D(2, 4 ), new Vector2D(2, 2 ));
        assertEquals(new Vector2D(2, 2 ), barrier3.getStart());
        assertEquals(new Vector2D(2, 4 ), barrier3.getEnd());
    }

    @Test
    public void noDiagonalBarriersTest() {
        assertThrows(RuntimeException.class, () -> new Barrier(new Vector2D(1, 1 ), new Vector2D(2, 2 )));
        assertThrows(RuntimeException.class, () -> new Barrier(new Vector2D(2, 1 ), new Vector2D(5, 6 )));
    }

    @Test
    public void noNegativeVector2DsTest() {
        assertThrows(RuntimeException.class, () -> new Vector2D(-2, 1));
        assertThrows(RuntimeException.class, () -> new Vector2D(2, -1));
    }

    @Test
    public void noNegativeStepsOnTaskTest() {
        assertThrows(RuntimeException.class, () -> new Task(TaskType.NORTH, -2));
    }
}
