package thkoeln.archilab.bauzeichner20.functionaltests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.bauzeichner20.solution.Canvas;
import thkoeln.archilab.bauzeichner20.solution.CanvasHandler;
import thkoeln.archilab.bauzeichner20.solution.DrawingElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thkoeln.archilab.bauzeichner20.solution.DirectionType.LEFT;

@SpringBootTest
public class CanvasTest {
    private Canvas canvas;
    @Autowired
    private CanvasHandler canvasHandler;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateCanvas() {
        // given
        Integer width = 1000;
        Integer height = 350;

        // when
        Canvas canvas = canvasHandler.createCanvasWithDoor( width, height );
        DrawingElement door = canvas.getDrawingElements().get( 0 );

        // then
        assertEquals( width, canvas.getWidth() );
        assertEquals( height, canvas.getHeight() );
        assertEquals( 440, door.edgePositionAt( LEFT ) );
    }

}
