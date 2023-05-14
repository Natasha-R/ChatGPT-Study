package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectValidationTests {

    @Test
    public void ensureObstacleVector2DsAreOrderedTest() {
        Obstacle obstacle1 = new Obstacle(new Vector2D(2, 1 ), new Vector2D(4, 1 ));
        assertEquals(new Vector2D(2, 1 ), obstacle1.getStart());
        assertEquals(new Vector2D(4, 1 ), obstacle1.getEnd());

        Obstacle obstacle2 = new Obstacle(new Vector2D(4, 1 ), new Vector2D(2, 1 ));
        assertEquals(new Vector2D(2, 1 ), obstacle2.getStart());
        assertEquals(new Vector2D(4, 1 ), obstacle2.getEnd());

        Obstacle obstacle3 = new Obstacle(new Vector2D(2, 4 ), new Vector2D(2, 2 ));
        assertEquals(new Vector2D(2, 2 ), obstacle3.getStart());
        assertEquals(new Vector2D(2, 4 ), obstacle3.getEnd());
    }

    @Test
    public void noDiagonalObstacleTest() {
        assertThrows(RuntimeException.class, () -> new Obstacle(new Vector2D(1, 1 ), new Vector2D(2, 2 )));
        assertThrows(RuntimeException.class, () -> new Obstacle(new Vector2D(2, 1 ), new Vector2D(5, 6 )));
    }

    @Test
    public void noNegativeVector2DTest() {
        assertThrows(RuntimeException.class, () -> new Vector2D(-2, 1));
        assertThrows(RuntimeException.class, () -> new Vector2D(2, -1));
    }

    @Test
    public void noNegativeStepsOnCommandTest() {
        assertThrows(RuntimeException.class, () -> new Command(CommandType.NORTH, -2));
    }
}
