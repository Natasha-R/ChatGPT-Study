package thkoeln.archilab.bauzeichner20.solution;

import java.util.List;
import java.util.UUID;

public interface ICanvas {
    UUID getId();
    String getName();
    Integer getWidth();
    Integer getHeight();
    List<IDrawingElement> getDrawingElements();
    void addDrawingElement(IDrawingElement drawingElement);
}
