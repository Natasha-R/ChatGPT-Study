package thkoeln.archilab.bauzeichner20.solution;

import org.springframework.stereotype.Service;

@Service
public class DrawingElementHandler implements IDrawingElementHandler{
    private DrawingElementRepo drawingElementRepo;

    public DrawingElementHandler( DrawingElementRepo drawingElementRepo ) {
        this.drawingElementRepo = drawingElementRepo;
    }

    @Override
    public void save(IDrawingElement drawingElement) {
        if (drawingElement instanceof DrawingElement) {
            drawingElementRepo.save((DrawingElement) drawingElement);
        } else {
            throw new IllegalArgumentException("Invalid DrawingElement implementation");
        }
    }
}
