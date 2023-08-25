package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectValidationTests {

    @Test
    public void ensureWallCoordinatesAreOrderedTest() {
        Wall wall1 = new Wall(new Coordinate(2, 1 ), new Coordinate(4, 1 ));
        assertEquals(new Coordinate(2, 1 ), wall1.getStart());
        assertEquals(new Coordinate(4, 1 ), wall1.getEnd());

        Wall wall2 = new Wall(new Coordinate(4, 1 ), new Coordinate(2, 1 ));
        assertEquals(new Coordinate(2, 1 ), wall2.getStart());
        assertEquals(new Coordinate(4, 1 ), wall2.getEnd());

        Wall wall3 = new Wall(new Coordinate(2, 4 ), new Coordinate(2, 2 ));
        assertEquals(new Coordinate(2, 2 ), wall3.getStart());
        assertEquals(new Coordinate(2, 4 ), wall3.getEnd());
    }

    @Test
    public void noDiagonalWallsTest() {
        assertThrows(RuntimeException.class, () -> new Wall(new Coordinate(1, 1 ), new Coordinate(2, 2 )));
        assertThrows(RuntimeException.class, () -> new Wall(new Coordinate(2, 1 ), new Coordinate(5, 6 )));
    }

    @Test
    public void noNegativeCoordinatesTest() {
        assertThrows(RuntimeException.class, () -> new Coordinate(-2, 1));
        assertThrows(RuntimeException.class, () -> new Coordinate(2, -1));
    }

    @Test
    public void noNegativeStepsOnTaskTest() {
        assertThrows(RuntimeException.class, () -> new Task(TaskType.NORTH, -2));
    }
}
