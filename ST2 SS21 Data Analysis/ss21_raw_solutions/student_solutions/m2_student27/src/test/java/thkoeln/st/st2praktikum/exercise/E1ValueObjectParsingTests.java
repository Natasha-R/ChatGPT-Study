package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import java.util.IllegalFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectParsingTests {

    @Test
    public void moveOrderParsingTest() {
        Order moveOrder = new Order("[so,4]");
        assertEquals(OrderType.SOUTH, moveOrder.getOrderType());
        assertEquals(Integer.valueOf(4), moveOrder.getNumberOfSteps());
    }

    @Test
    public void orderFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> new Order("[soo,4]"));
        assertThrows(RuntimeException.class, () -> new Order("[4, no]"));
        assertThrows(RuntimeException.class, () -> new Order("[[soo,4]]"));
        assertThrows(RuntimeException.class, () -> new Order("(soo,4)"));
    }

    @Test
    public void pointParsingTest() {
        Point point = new Point("(2,3)");
        assertEquals(new Point(2, 3), point);
    }

    @Test
    public void pointFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> new Point("(2,,3)"));
        assertThrows(RuntimeException.class, () -> new Point("((2,3]"));
        assertThrows(RuntimeException.class, () -> new Point("(1,2,3)"));
        assertThrows(RuntimeException.class, () -> new Point("(1,)"));
    }

    @Test
    public void obstacleParsingTest() {
        Obstacle obstacle = new Obstacle("(1,2)-(1,4)");
        assertEquals(new Point(1, 2), obstacle.getStart());
        assertEquals(new Point(1, 4), obstacle.getEnd());
    }

    @Test
    public void obstacleParsingFailedTest() {
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,2-1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,2),(1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,)-(1,4)"));
        assertThrows(RuntimeException.class, () -> new Obstacle("(1,4)"));
    }


}