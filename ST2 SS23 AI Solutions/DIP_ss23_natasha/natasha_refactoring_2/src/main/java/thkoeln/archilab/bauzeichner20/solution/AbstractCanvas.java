package thkoeln.archilab.bauzeichner20.solution;

import java.util.List;

public abstract class AbstractCanvas {
    abstract Integer getWidth();
   abstract Integer getHeight();
    abstract List<DrawingElement> getNeighboursOf(DrawingElement drawingElement );
}
