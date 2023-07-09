package thkoeln.st.st2praktikum.exercise;

public interface Moveable {
    public void moveNorth(Field field, int numberOfSteps);
    public void moveEast(Field field, int numberOfSteps);
    public void moveSouth(Field field, int numberOfSteps);
    public void moveWest(Field field, int numberOfSteps);
}
