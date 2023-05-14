package thkoeln.st.st2praktikum.exercise;

public interface Movable {
    public Boolean moveNorth(Room room, Command command);
    public Boolean moveEast(Room room, Command command);
    public Boolean moveSouth(Room room, Command command);
    public Boolean moveWest(Room room, Command command);
}
