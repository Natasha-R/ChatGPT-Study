package thkoeln.st.st2praktikum.exercise.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.DevelopmentTests;
import thkoeln.st.st2praktikum.exercise.OrderType;

import static org.junit.jupiter.api.Assertions.*;

public class BarrierTests extends DevelopmentTests {

    @BeforeEach
    public void initEach() {
        createWorld();
    }

    @Test
    public void barrierTest() {


        testGrid.addBarriers( testBarrier1 );
        testGrid.addBarriers( testBarrier2 );

        assertEquals( "(1,2)", testBarrier1.getStart().toString() );
        assertEquals( "(2,2)", testBarrier1.getEnd().toString() );

        assertTrue( testPoint.connectionsContains( OrderType.WEST ) );
        assertTrue( testPoint.connectionsContains( OrderType.SOUTH ) );
        assertFalse( testPoint.connectionsContains( OrderType.NORTH ) );
        assertFalse( testPoint.connectionsContains( OrderType.EAST ) );
    }


    @Test
    public void isVerticalTest() {

        assertFalse(testBarrier1.isVertical());
        assertTrue( testBarrier2.isVertical());
    }

    @Test
    public void isHorizontalTest() {

        assertTrue(testBarrier1.isHorizontal());
        assertFalse( testBarrier2.isHorizontal());
    }


}
