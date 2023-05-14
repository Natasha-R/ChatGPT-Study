package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectValidationTests {

    @Test
    public void ensureBarrierCoordinatesAreOrderedTest() {
        Barrier barrier1 = new Barrier(new Coordinate(2, 1 ), new Coordinate(4, 1 ));
        assertEquals(new Coordinate(2, 1 ), barrier1.getStart());
        assertEquals(new Coordinate(4, 1 ), barrier1.getEnd());

        Barrier barrier2 = new Barrier(new Coordinate(4, 1 ), new Coordinate(2, 1 ));
        assertEquals(new Coordinate(2, 1 ), barrier2.getStart());
        assertEquals(new Coordinate(4, 1 ), barrier2.getEnd());

        Barrier barrier3 = new Barrier(new Coordinate(2, 4 ), new Coordinate(2, 2 ));
        assertEquals(new Coordinate(2, 2 ), barrier3.getStart());
        assertEquals(new Coordinate(2, 4 ), barrier3.getEnd());
    }

    @Test
    public void noDiagonalBarrierTest() {
        assertThrows(RuntimeException.class, () -> new Barrier(new Coordinate(1, 1 ), new Coordinate(2, 2 )));
        assertThrows(RuntimeException.class, () -> new Barrier(new Coordinate(2, 1 ), new Coordinate(5, 6 )));
    }

    @Test
    public void noNegativeCoordinateTest() {
        assertThrows(RuntimeException.class, () -> new Coordinate(-2, 1));
        assertThrows(RuntimeException.class, () -> new Coordinate(2, -1));
    }

    @Test
    public void noNegativeStepsOnOrderTest() {
        assertThrows(RuntimeException.class, () -> new Order(OrderType.NORTH, -2));
    }
}
