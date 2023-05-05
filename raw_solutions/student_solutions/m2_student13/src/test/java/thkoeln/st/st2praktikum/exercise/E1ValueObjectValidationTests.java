package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectValidationTests {


    // (1,2)-(3,2)
    @Test
    public void ensureWallPointsAreOrderedTest() {
        Wall wall1 = new Wall(new Point(2, 1 ), new Point(4, 1 ));
        assertEquals(new Point(2, 1 ), wall1.getStart());
        assertEquals(new Point(4, 1 ), wall1.getEnd());

        Wall wall2 = new Wall(new Point(4, 1 ), new Point(2, 1 ));
        assertEquals(new Point(2, 1 ), wall2.getStart());
        assertEquals(new Point(4, 1 ), wall2.getEnd());

        Wall wall3 = new Wall(new Point(2, 4 ), new Point(2, 2 ));
        assertEquals(new Point(2, 2 ), wall3.getStart());
        assertEquals(new Point(2, 4 ), wall3.getEnd());
    }

    @Test
    public void noDiagonalWallsTest() {
        assertThrows(RuntimeException.class, () -> new Wall(new Point(1, 1 ), new Point(2, 2 )));
        assertThrows(RuntimeException.class, () -> new Wall(new Point(2, 1 ), new Point(5, 6 )));
    }

    @Test
    public void noNegativePointsTest() {
        assertThrows(RuntimeException.class, () -> new Point(-2, 1));
        assertThrows(RuntimeException.class, () -> new Point(2, -1));
    }

    @Test
    public void noNegativeStepsOnTaskTest() {
        assertThrows(RuntimeException.class, () -> new Task(TaskType.NORTH, -2));
    }
}
