package thkoeln.archilab.bauzeichner20.solution;

import java.util.UUID;

public interface IDrawingElement {
    UUID getId();
    Integer getX_bottom_left();
    Integer getY_bottom_left();
    Integer getWidth();
    Integer getHeight();
    DrawingElementType getDrawingElementType();
    ICanvas getCanvas();
    void move(DirectionType direction, Integer distance);
    Integer edgePositionAt(DirectionType direction);

    void setCanvas(ICanvas canvas);
}