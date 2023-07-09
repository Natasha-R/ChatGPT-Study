package thkoeln.st.st2praktikum.exercise;

public class SingleWall {
    private final int x;
    private final int y;

    public SingleWall(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SingleWall() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
