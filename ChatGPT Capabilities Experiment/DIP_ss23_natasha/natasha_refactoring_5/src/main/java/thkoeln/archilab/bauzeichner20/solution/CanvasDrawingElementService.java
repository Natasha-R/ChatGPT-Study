package thkoeln.archilab.bauzeichner20.solution;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CanvasDrawingElementService {

    private final CanvasRepo canvasRepo;
    private final DrawingElementHandler drawingElementHandler;

    public CanvasDrawingElementService(CanvasRepo canvasRepo, DrawingElementHandler drawingElementHandler) {
        this.canvasRepo = canvasRepo;
        this.drawingElementHandler = drawingElementHandler;
    }

    public void addDrawingElementToCanvas(ICanvas canvas, IDrawingElement drawingElement) {
        // Check any preconditions or business rules here before adding
        canvas.getDrawingElements().add(drawingElement);
        drawingElement.setCanvas(canvas);
        canvasRepo.save((Canvas) canvas);
        drawingElementHandler.save((DrawingElement) drawingElement);
    }

    public void removeDrawingElementFromCanvas(ICanvas canvas, IDrawingElement drawingElement) {
        // Check any preconditions or business rules here before removing
        canvas.getDrawingElements().remove(drawingElement);
        drawingElement.setCanvas(null);
        canvasRepo.save((Canvas) canvas);
        drawingElementHandler.save((DrawingElement) drawingElement);
    }

    public List<IDrawingElement> getNeighboursOf(ICanvas canvas, IDrawingElement drawingElement) {
        List<IDrawingElement> neighbours = new ArrayList<>();
        for (IDrawingElement element : canvas.getDrawingElements()) {
            if (element != drawingElement) {
                neighbours.add(element);
            }
        }
        return neighbours;
    }
}

