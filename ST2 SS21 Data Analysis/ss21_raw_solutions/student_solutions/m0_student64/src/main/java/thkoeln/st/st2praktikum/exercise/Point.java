package thkoeln.st.st2praktikum.exercise;

public class Point {

    private int x;
    private int y;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addToX(int x) {
        this.x += x;
    }

    public void addToY(int y) {
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
