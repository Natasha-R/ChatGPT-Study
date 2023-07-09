package thkoeln.archilab.bauzeichner20.solution;

import org.springframework.stereotype.Service;

@Service
public class CanvasHandler {
    public static final String DEFAULT_CANVAS_NAME = "Default";
    private CanvasRepo canvasRepo;
    private IDrawingElementHandler drawingElementHandler;

    public CanvasHandler(CanvasRepo canvasRepo, IDrawingElementHandler drawingElementHandler) {
        this.canvasRepo = canvasRepo;
        this.drawingElementHandler = drawingElementHandler;
    }

    public ICanvas createCanvasWithDoor(Integer width, Integer height) {
        ICanvas canvas = new Canvas(width, height);
        IDrawingElement door = DrawingElement.door(100, 0, 120, 200);
        drawingElementHandler.save(door);
        Integer doorPositionLeft = canvas.getWidth() / 2 - door.getWidth() / 2;
        canvas.getDrawingElements().add( door );
        door.setX_bottom_left( doorPositionLeft );
        door.setCanvas( canvas );
        if (canvas instanceof Canvas) {
            canvasRepo.save((Canvas) canvas);
        } else {
            throw new IllegalArgumentException("Invalid DrawingElement implementation");
        }
        drawingElementHandler.save( door );
        return canvas;
    }
}
