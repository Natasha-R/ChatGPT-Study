package thkoeln.st.st2praktikum.exercise;

public interface Moveable {
    boolean move(Field field, String moveCommand);
    void moveNorth(int numberOfSteps, Field field);
    void moveEast(int numberOfSteps, Field field);
    void moveSouth(int numberOfSteps, Field field);
    void moveWest(int numberOfSteps, Field field);
}
