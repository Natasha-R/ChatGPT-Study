package thkoeln.st.st2praktikum.exercise;

public interface Transportable {
    public Boolean transportTo (TidyUpRobot tidyUpRobot, Room currentRoom, Room destinationRoom, Command command);
}
