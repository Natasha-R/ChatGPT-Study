package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectValidationTests {

    @Test
    public void ensureObstacleCoordinatesAreOrderedTest() {
        Obstacle obstacle1 = new Obstacle(new Coordinate(2, 1 ), new Coordinate(4, 1 ));
        assertEquals(new Coordinate(2, 1 ), obstacle1.getStart());
        assertEquals(new Coordinate(4, 1 ), obstacle1.getEnd());

        Obstacle obstacle2 = new Obstacle(new Coordinate(4, 1 ), new Coordinate(2, 1 ));
        assertEquals(new Coordinate(2, 1 ), obstacle2.getStart());
        assertEquals(new Coordinate(4, 1 ), obstacle2.getEnd());

        Obstacle obstacle3 = new Obstacle(new Coordinate(2, 4 ), new Coordinate(2, 2 ));
        assertEquals(new Coordinate(2, 2 ), obstacle3.getStart());
        assertEquals(new Coordinate(2, 4 ), obstacle3.getEnd());
    }

    @Test
    public void noDiagonalObstacleTest() {
        assertThrows(RuntimeException.class, () -> new Obstacle(new Coordinate(1, 1 ), new Coordinate(2, 2 )));
        assertThrows(RuntimeException.class, () -> new Obstacle(new Coordinate(2, 1 ), new Coordinate(5, 6 )));
    }

    @Test
    public void noNegativeCoordinateTest() {
        assertThrows(RuntimeException.class, () -> new Coordinate(-2, 1));
        assertThrows(RuntimeException.class, () -> new Coordinate(2, -1));
    }

    @Test
    public void noNegativeStepsOnCommandTest() {
        assertThrows(RuntimeException.class, () -> new Command(CommandType.NORTH, -2));
    }
}
