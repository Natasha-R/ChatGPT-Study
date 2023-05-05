package thkoeln.st.st2praktikum.exercise;

public class Position {

    private int x, y;

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}