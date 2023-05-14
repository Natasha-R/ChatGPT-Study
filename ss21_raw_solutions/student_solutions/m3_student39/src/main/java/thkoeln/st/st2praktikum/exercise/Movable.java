package thkoeln.st.st2praktikum.exercise;

public interface Movable {
    public void placeOnMap(Field map, Vector2D newPosition);
    public boolean useConnection(Field desiredMap);
    public boolean move(Order moveCommand);
}
