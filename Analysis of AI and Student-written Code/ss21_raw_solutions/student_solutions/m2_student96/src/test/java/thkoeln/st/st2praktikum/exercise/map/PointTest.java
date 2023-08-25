package thkoeln.st.st2praktikum.exercise.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.converter.InvalidStringException;
import thkoeln.st.st2praktikum.exercise.core.DevelopmentTests;
import thkoeln.st.st2praktikum.exercise.OrderType;

import static org.junit.jupiter.api.Assertions.*;


public class PointTest extends DevelopmentTests {

    @BeforeEach
    public void initEach() {
        createWorld();
    }


    @Test
    public void ConstructorTest () {
        testPoint = new Point( 1,1);

        assertEquals("(1,1)", testPoint.toString());

    }

    @Test
    public void InvalidPointTest () {

        //given
        //Test point 3 with invalid parameers
        //when
        //then
        assertThrows( PointException.class, () ->
                testPoint = new Point( -1 , 0 ));
        assertThrows( PointException.class, () ->
                testPoint = new Point( 0 , -1 ));

    }

    @Test
    public void constructPointFromStringTest() {

        testPoint = new Point("(0,2)");
        assertEquals(0, (int) testPoint.getX());
        assertEquals(2, (int) testPoint.getY());
    }

    @Test
    public void constructPointFromWrongStringTest() {

        assertThrows( InvalidStringException.class, () ->
                testPoint = new Point( "(-1,0)"));
        assertThrows( InvalidStringException.class, () ->
                testPoint = new Point( "(0,-1)"));
        assertThrows( InvalidStringException.class, () ->
                testPoint = new Point( "[en,1328790510987615t]"));
    }

    @Test
    public void makeConnectionTest() {

        testPoint.makeSouthernConnection(southernPoint);
        testPoint.makeWesternConnection(westernPoint);

        assertEquals( westernPoint , testPoint.getWest() );
        assertEquals( southernPoint , testPoint.getSouth() );
    }

    @Test
    public void makeConnectionFailTest() {

        assertThrows(PointException.class, () ->
                testPoint.makeSouthernConnection(westernPoint));
        assertThrows(PointException.class, () ->
                testPoint.makeWesternConnection(southernPoint));
    }

    @Test
    public void removeConnectionTest() {

        testPoint.makeSouthernConnection(southernPoint);
        testPoint.makeWesternConnection(westernPoint);

        assertEquals( westernPoint , testPoint.getWest() );
        assertEquals( southernPoint , testPoint.getSouth() );

        testPoint.removeWesternConnection();
        testPoint.removeSouthernConnection();

        assertFalse(testPoint.getConnections().containsKey( OrderType.WEST ) );
        assertFalse(testPoint.getConnections().containsKey( OrderType.SOUTH ) );
        assertFalse(westernPoint.getConnections().containsKey( OrderType.EAST ) );
        assertFalse(southernPoint.getConnections().containsKey( OrderType.NORTH ));
    }

    @Test
    public void getConnectionFailTest() {
        assertThrows( PointException.class, () -> southernPoint.getSouth() );
    }

}
