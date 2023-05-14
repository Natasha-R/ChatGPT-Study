package thkoeln.st.st2praktikum.exercise;

public interface Transportable {
    public Boolean transportTo(Room currentRoom, Room destinationRoom, Command command);
}
