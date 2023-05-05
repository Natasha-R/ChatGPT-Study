package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

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
    public void obstacleParsingTest() {
        Obstacle obstacle = new Obstacle("(1,2)-(1,4)");
        assertEquals(new Vector2D(1, 2), obstacle.getStart());
        assertEquals(new Vector2D(1, 4), obstacle.getEnd());
    }

    @Test
    public void obstacleParsingFailedTest() {
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,2-1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,2),(1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,)-(1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,4)"));
    }


}
