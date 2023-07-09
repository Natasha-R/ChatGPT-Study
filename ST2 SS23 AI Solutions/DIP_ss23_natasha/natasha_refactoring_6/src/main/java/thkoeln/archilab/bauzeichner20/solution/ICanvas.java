package thkoeln.archilab.bauzeichner20.solution;

import java.util.List;

public interface ICanvas {
    void addDrawingElement(IDrawingElement drawingElement);
    List<IDrawingElement> getNeighboursOf(IDrawingElement drawingElement);
    Integer getWidth();
    Integer getHeight();
    List<IDrawingElement> getDrawingElements();

}
