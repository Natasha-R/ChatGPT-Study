package thkoeln.archilab.bauzeichner20.solution.drawingelement.application;

import org.springframework.stereotype.Service;
import thkoeln.archilab.bauzeichner20.solution.canvas.application.DrawableServiceInterface;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Drawable;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.Door;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.DrawingElement;
import thkoeln.archilab.bauzeichner20.solution.drawingelement.domain.DrawingElementRepository;

@Service
public class DrawableService implements DrawableServiceInterface {
    private DrawingElementRepository drawingElementRepository;

    public DrawableService( DrawingElementRepository drawingElementRepository ) {
        this.drawingElementRepository = drawingElementRepository;
    }

    public void save( Drawable drawable ) {
        drawingElementRepository.save( (DrawingElement) drawable );
    }

    @Override
    public Drawable createDefaultDoor() {
        return new Door( 100, 0, 120, 200 );
    }
}
