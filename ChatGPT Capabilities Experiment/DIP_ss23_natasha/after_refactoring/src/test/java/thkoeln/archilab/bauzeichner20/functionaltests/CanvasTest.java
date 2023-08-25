package thkoeln.archilab.bauzeichner20.functionaltests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Canvas;
import thkoeln.archilab.bauzeichner20.solution.canvas.application.CanvasService;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Drawable;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.DrawingElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thkoeln.archilab.bauzeichner20.solution.canvas.domain.DirectionType.LEFT;

@SpringBootTest
public class CanvasTest {
    private Canvas canvas;
    @Autowired
    private CanvasService canvasService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateCanvas() {
        // given
        Integer width = 1000;
        Integer height = 350;

        // when
        Canvas canvas = canvasService.createCanvasWithDoor( width, height );
        Drawable door = canvas.getDrawables().get( 0 );

        // then
        assertEquals( width, canvas.getWidth() );
        assertEquals( height, canvas.getHeight() );
        assertEquals( 440, ((DrawingElement) door).edgePositionAt( LEFT ) );
    }

}
