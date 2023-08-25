package thkoeln.archilab.bauzeichner20.solution;
import java.util.List;

public interface ICanvas {
    Integer getWidth();
    Integer getHeight();
    List<DrawingElement> getNeighboursOf(DrawingElement drawingElement);
}

