package thkoeln.st.st2praktikum.exercise.room;

public abstract class AbstractObstacle {
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    public abstract boolean collision(int x, int y);
}
