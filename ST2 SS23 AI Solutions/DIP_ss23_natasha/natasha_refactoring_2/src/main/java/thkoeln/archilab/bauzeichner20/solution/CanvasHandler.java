package thkoeln.archilab.bauzeichner20.solution;

import org.springframework.stereotype.Service;
import thkoeln.archilab.bauzeichner20.BauzeichnerException;

@Service
public class CanvasHandler {
    public static final String DEFAULT_CANVAS_NAME = "Default";
    private CanvasRepo canvasRepo;
    private DrawingElementHandler drawingElementHandler;

    public CanvasHandler( CanvasRepo canvasRepo, DrawingElementHandler drawingElementHandler ) {
        this.canvasRepo = canvasRepo;
        this.drawingElementHandler = drawingElementHandler;
    }

    public Canvas createCanvas(Integer width, Integer height, String name) {
        Canvas canvas = new Canvas();
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.setName(name);
        canvasRepo.save(canvas);
        return canvas;
    }

    public void addDrawingElement(Canvas canvas, DrawingElement drawingElement) {
        if (drawingElement == null) {
            throw new BauzeichnerException("DrawingElement must not be null");
        }
        canvas.getDrawingElements().add(drawingElement);
        drawingElement.setCanvas(canvas);
        canvasRepo.save(canvas);
    }

    public Canvas createCanvasWithDoor(Integer width, Integer height) {
        Canvas canvas = createCanvas(width, height, DEFAULT_CANVAS_NAME);
        DrawingElement door = DrawingElement.door(100, 0, 120, 200);
        Integer doorPositionLeft = canvas.getWidth() / 2 - door.getWidth() / 2;
        door.setX_bottom_left(doorPositionLeft);
        addDrawingElement(canvas, door);
        drawingElementHandler.save(door);
        return canvas;
    }
}
