package thkoeln.st.st2praktikum.exercise;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void incrementX (int amount) {
        this.x += amount;
    }

    public void decrementX (int amount) {
        this.x -= amount;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incrementY (int amount) {
        this.y += amount;
    }

    public void decrementY (int amount) {
        this.y -= amount;
    }


    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
