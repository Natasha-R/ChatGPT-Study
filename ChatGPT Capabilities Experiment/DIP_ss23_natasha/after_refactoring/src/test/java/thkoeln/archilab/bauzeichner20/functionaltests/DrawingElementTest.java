package thkoeln.archilab.bauzeichner20.functionaltests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.archilab.bauzeichner20.BauzeichnerException;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.Door;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.DrawingElement;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.Window;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static thkoeln.archilab.bauzeichner20.solution.canvas.domain.DirectionType.*;

public class DrawingElementTest {
    private DrawingElement door1, door2, window1Bottom, window1Top, window2;

    @BeforeEach
    public void setup() {
        door1 = new Door( 100, 0, 120, 210 );
        door2 = new Door( 600, 0, 140, 250 );
        window1Bottom = new Window( 250, 70, 50, 50 );
        window1Top = new Window( 260, 140, 30, 80 );
        window2 = new Window( 740, 300, 100, 40 );
    }

    @Test
    public void testHorizontalDistance() {
        // given
        // when
        Integer door1Door2Distance = door1.horizontalDistanceTo( door2 );
        Integer door2Door1Distance = door2.horizontalDistanceTo( door1 );
        Integer window1BottomWindow1TopDistance = window1Bottom.horizontalDistanceTo( window1Top );
        Integer door1Window1BottomDistance = door1.horizontalDistanceTo( window1Bottom );
        Integer window1TopWindow2Distance = window1Top.horizontalDistanceTo( window2 );
        Integer window2Door2Distance = window2.horizontalDistanceTo( door2 );

        // then
        assertEquals( 380, door1Door2Distance );
        assertEquals( 380, door2Door1Distance );
        assertEquals( -1, window1BottomWindow1TopDistance );
        assertEquals( 30, door1Window1BottomDistance );
        assertEquals( 450, window1TopWindow2Distance  );
        assertEquals( 0, window2Door2Distance );
    }



    @Test
    public void testVerticalDistance() {
        // given
        // when
        Integer door1Door2Distance = door1.verticalDistanceTo( door2 );
        Integer door2Door1Distance = door2.verticalDistanceTo( door1 );
        Integer window1BottomWindow1TopDistance = window1Bottom.verticalDistanceTo( window1Top );
        Integer door1Window1BottomDistance = door1.verticalDistanceTo( window1Bottom );
        Integer window1TopWindow2Distance = window1Top.verticalDistanceTo( window2 );
        Integer window2Door2Distance = window2.verticalDistanceTo( door2 );


        // then
        assertEquals( -1, door1Door2Distance );
        assertEquals( -1, door2Door1Distance );
        assertEquals( 20, window1BottomWindow1TopDistance );
        assertEquals( -1, door1Window1BottomDistance );
        assertEquals( 80, window1TopWindow2Distance  );
        assertEquals( 50, window2Door2Distance );
    }


    @Test
    public void testMoveInvalid() {
        // given
        // when
        assertThrows( BauzeichnerException.class, () -> door1.move( BOTTOM, 10 ) );
    }

    @Test
    public void testMoveValidWithoutCanvas() {
        // given
        window1Top.move( TOP, 10 );
        door2.move( RIGHT, 10 );

        // when
       Integer topEdgeWindow1Top = window1Top.edgePositionAt( TOP );
       Integer rightEdgeDoor2 = door2.edgePositionAt( RIGHT );

       // then
        assertEquals( 230, topEdgeWindow1Top );
        assertEquals( 750, rightEdgeDoor2 );
    }

}
