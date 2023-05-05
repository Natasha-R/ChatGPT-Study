package thkoeln.st.st2praktikum.exercise;

public interface Movable {
    public void placeOnMap(Map map, Vector2D newPosition);
    public boolean useConnection(Map desiredMap);
    public boolean move(Order moveCommand);
}
