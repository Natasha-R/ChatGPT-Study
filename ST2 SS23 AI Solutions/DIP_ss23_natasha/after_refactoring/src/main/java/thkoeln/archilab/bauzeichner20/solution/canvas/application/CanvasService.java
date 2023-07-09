package thkoeln.archilab.bauzeichner20.solution.canvas.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Canvas;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.CanvasRepository;
import thkoeln.archilab.bauzeichner20.solution.canvas.domain.Drawable;

@Service
public class CanvasService {
    public static final String DEFAULT_CANVAS_NAME = "Default";
    private CanvasRepository canvasRepository;
    private DrawableServiceInterface drawableServiceInterface;

    @Autowired
    public CanvasService( CanvasRepository canvasRepository,
                          DrawableServiceInterface drawableServiceInterface ) {
        this.canvasRepository = canvasRepository;
        this.drawableServiceInterface = drawableServiceInterface;
    }

    public Canvas createCanvasWithDoor( Integer width, Integer height ) {
        Canvas canvas = new Canvas( width, height );
        Drawable door = drawableServiceInterface.createDefaultDoor();
        drawableServiceInterface.save( door );
        Integer doorPositionLeft = canvas.getWidth() / 2 - door.width() / 2;
        canvas.getDrawables().add( door );
        door.setXBottomLeft( doorPositionLeft );
        door.setCanvas( canvas );
        canvasRepository.save( canvas );
        drawableServiceInterface.save( door );
        return canvas;
    }
}
