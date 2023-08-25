package thkoeln.archilab.bauzeichner20.solution;

public interface IDrawingElement {
    void move(DirectionType direction, Integer distance);
    Integer edgePositionAt(DirectionType direction);
    Integer verticalDistanceTo(IDrawingElement drawingElement);
    Integer horizontalDistanceTo(IDrawingElement drawingElement);
    Integer getX_bottom_left();
    Integer getY_bottom_left();
    Integer getWidth();
    Integer getHeight();
    void setX_bottom_left(Integer x_bottom_left);
    void setY_bottom_left(Integer y_bottom_left);
    void setWidth(Integer width);
    void setHeight(Integer height);
    void setCanvas(ICanvas canvas);

}
