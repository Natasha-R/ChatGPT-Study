package thkoeln.archilab.bauzeichner20.solution;

import org.springframework.stereotype.Service;

@Service
public class DrawingElementHandler {
    private DrawingElementRepo drawingElementRepo;

    public DrawingElementHandler( DrawingElementRepo drawingElementRepo ) {
        this.drawingElementRepo = drawingElementRepo;
    }

    public void save( DrawingElement drawingElement ) {
        drawingElementRepo.save( drawingElement );
    }
}
