package thkoeln.archilab.bauzeichner20.solution;

public interface Drawable {
    void setCanvas(Canvas canvas);

    Integer getX_bottom_left();

    Integer getY_bottom_left();

    Integer getWidth();

    Integer getHeight();
}
