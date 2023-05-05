package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import java.util.IllegalFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectParsingTests {

    @Test
    public void moveCommandParsingTest() {
        Command moveCommand = new Command("[so,4]");
        assertEquals(CommandType.SOUTH, moveCommand.getCommandType());
        assertEquals(Integer.valueOf(4), moveCommand.getNumberOfSteps());
    }

    @Test
    public void commandFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> new Command("[soo,4]"));
        assertThrows(RuntimeException.class, () -> new Command("[4, no]"));
        assertThrows(RuntimeException.class, () -> new Command("[[soo,4]]"));
        assertThrows(RuntimeException.class, () -> new Command("(soo,4)"));
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
    public void barrierParsingTest() {
        Barrier barrier = new Barrier("(1,2)-(1,4)");
        assertEquals(new Vector2D(1, 2), barrier.getStart());
        assertEquals(new Vector2D(1, 4), barrier.getEnd());
    }

    @Test
    public void barrierParsingFailedTest() {
        assertThrows(RuntimeException.class, () -> new Barrier("(1,2-1,4)"));
        assertThrows(RuntimeException.class, () -> new Barrier("(1,2),(1,4)"));
        assertThrows(RuntimeException.class, () -> new Barrier("(1,)-(1,4)"));
        assertThrows(RuntimeException.class, () -> new Barrier("(1,4)"));
    }


}
