package thkoeln.st.st2praktikum.exercise;
public interface Movable {
    void move(String direction, int steps, int[][] obstacles, int maxX, int maxY);
}