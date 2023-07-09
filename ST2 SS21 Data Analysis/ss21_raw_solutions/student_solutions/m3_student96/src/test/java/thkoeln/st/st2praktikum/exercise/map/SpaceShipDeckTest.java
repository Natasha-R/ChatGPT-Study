package thkoeln.st.st2praktikum.exercise.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.DevelopmentTests;

public class SpaceShipDeckTest extends DevelopmentTests {


    @BeforeEach
    public void initEach() {
        createWorld();
    }

    @Test
    public void gridConstructorTest() {

        Assertions.assertEquals( 25, testSpaceShipDeck.getPoints().size() );
        Assertions.assertEquals( 5, testSpaceShipDeck.getHeight() );
        Assertions.assertEquals( 5, testSpaceShipDeck.getWidth() );

    }

    @Test
    public void getPointFromGrid() {

        Assertions.assertEquals( "(0,0)" , testSpaceShipDeck.getPointFromSpaceShipDeck("(0,0)" ).toString() );

    }

    @Test
    public void gridConnectionTest() {

        Assertions.assertEquals( "(2,1)", testPoint.getEast().toString() );
        Assertions.assertEquals( "(0,1)", testPoint.getWest().toString() );
        Assertions.assertEquals( "(1,2)", testPoint.getNorth().toString() );
        Assertions.assertEquals( "(1,0)", testPoint.getSouth().toString() );

    }

    @Test
    public void countConnections() {
        Assertions.assertEquals(4, testPoint.getConnections().size() );
        Assertions.assertEquals( 3, testPoint.getSouth().getConnections().size() );
        Assertions.assertEquals(2, testPoint.getSouth().getWest().getConnections().size() );
    }

    @Test
    public void getPointIsSameTest() {
        Assertions.assertSame( southernPoint, testPoint.getSouth() );
    }

}
