package thkoeln.st.st2praktikum.exercise;


public interface Movable {
    void placeOnMap(Map map, Vector2D newPosition);
    boolean useConnection(Map desiredMap);
    boolean move(Order moveCommand);
}
