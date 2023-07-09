package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectValidationTests {

    @Test
    public void ensureWallVector2DsAreOrderedTest() {
        Wall wall1 = new Wall(new Vector2D(2, 1 ), new Vector2D(4, 1 ));
        assertEquals(new Vector2D(2, 1 ), wall1.getStart());
        assertEquals(new Vector2D(4, 1 ), wall1.getEnd());

        Wall wall2 = new Wall(new Vector2D(4, 1 ), new Vector2D(2, 1 ));
        assertEquals(new Vector2D(2, 1 ), wall2.getStart());
        assertEquals(new Vector2D(4, 1 ), wall2.getEnd());

        Wall wall3 = new Wall(new Vector2D(2, 4 ), new Vector2D(2, 2 ));
        assertEquals(new Vector2D(2, 2 ), wall3.getStart());
        assertEquals(new Vector2D(2, 4 ), wall3.getEnd());
    }

    @Test
    public void noDiagonalWallTest() {
        assertThrows(RuntimeException.class, () -> new Wall(new Vector2D(1, 1 ), new Vector2D(2, 2 )));
        assertThrows(RuntimeException.class, () -> new Wall(new Vector2D(2, 1 ), new Vector2D(5, 6 )));
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
