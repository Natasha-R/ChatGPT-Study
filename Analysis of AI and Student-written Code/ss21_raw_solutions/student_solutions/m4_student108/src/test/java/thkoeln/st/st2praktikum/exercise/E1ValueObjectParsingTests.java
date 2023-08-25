package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.field.domain.Wall;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class E1ValueObjectParsingTests {

    @Test
    public void moveOrderParsingTest() {
        Order moveOrder = Order.fromString("[so,4]");
        assertEquals(OrderType.SOUTH, moveOrder.getOrderType());
        assertEquals(Integer.valueOf(4), moveOrder.getNumberOfSteps());
    }

    @Test
    public void orderFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> Order.fromString("[soo,4]"));
        assertThrows(RuntimeException.class, () -> Order.fromString("[4, no]"));
        assertThrows(RuntimeException.class, () -> Order.fromString("[[soo,4]]"));
        assertThrows(RuntimeException.class, () -> Order.fromString("(soo,4)"));
    }

    @Test
    public void pointParsingTest() {
        Point point = Point.fromString("(2,3)");
        assertEquals(new Point(2, 3), point);
    }

    @Test
    public void pointFailedParsingTest() {
        assertThrows(RuntimeException.class, () -> Point.fromString("(2,,3)"));
        assertThrows(RuntimeException.class, () -> Point.fromString("((2,3]"));
        assertThrows(RuntimeException.class, () -> Point.fromString("(1,2,3)"));
        assertThrows(RuntimeException.class, () -> Point.fromString("(1,)"));
    }

    @Test
    public void wallParsingTest() {
        Wall wall = Wall.fromString("(1,2)-(1,4)");
        assertEquals(new Point(1, 2), wall.getStart());
        assertEquals(new Point(1, 4), wall.getEnd());
    }

    @Test
    public void wallParsingFailedTest() {
        assertThrows(RuntimeException.class, () -> Wall.fromString("(1,2-1,4)"));
        assertThrows(RuntimeException.class, () -> Wall.fromString("(1,2),(1,4)"));
        assertThrows(RuntimeException.class, () -> Wall.fromString("(1,)-(1,4)"));
        assertThrows(RuntimeException.class, () -> Wall.fromString("(1,4)"));
    }


}
