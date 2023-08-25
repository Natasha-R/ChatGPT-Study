package thkoeln.archilab.bauzeichner20.solution.drawingelement.application;

import org.springframework.stereotype.Service;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.DrawingElement;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.DrawingElementRepository;

@Service
public class DrawingElementService {
    private DrawingElementRepository drawingElementRepo;

    public DrawingElementService( DrawingElementRepository drawingElementRepo ) {
        this.drawingElementRepo = drawingElementRepo;
    }

    public void save( DrawingElement drawingElement ) {
        drawingElementRepo.save( drawingElement );
    }
}
