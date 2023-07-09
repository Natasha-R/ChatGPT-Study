package thkoeln.archilab.bauzeichner20.solution;

import org.springframework.stereotype.Service;

@Service
public class CanvasHandler {
    public static final String DEFAULT_CANVAS_NAME = "Default";
    private CanvasRepo canvasRepo;
    private DrawingElementHandler drawingElementHandler;

    public CanvasHandler( CanvasRepo canvasRepo, DrawingElementHandler drawingElementHandler ) {
        this.canvasRepo = canvasRepo;
        this.drawingElementHandler = drawingElementHandler;
    }

    public Canvas createCanvasWithDoor( Integer width, Integer height ) {
        Canvas canvas = new Canvas( width, height );
        DrawingElement door = DrawingElement.door( 100, 0, 120, 200 );
        drawingElementHandler.save( door );
        Integer doorPositionLeft = canvas.getWidth() / 2 - door.getWidth() / 2;
        canvas.getDrawingElements().add( door );
        door.setX_bottom_left( doorPositionLeft );
        door.setCanvas( canvas );
        canvasRepo.save( canvas );
        drawingElementHandler.save( door );
        return canvas;
    }
}


