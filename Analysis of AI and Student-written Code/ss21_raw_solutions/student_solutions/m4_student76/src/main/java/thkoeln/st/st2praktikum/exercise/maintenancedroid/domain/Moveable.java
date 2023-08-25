package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

public interface Moveable {
    void moveNorth(int command);

    void moveSouth(int command);

    void moveWest(int command);

    void moveEast(int command);
}
