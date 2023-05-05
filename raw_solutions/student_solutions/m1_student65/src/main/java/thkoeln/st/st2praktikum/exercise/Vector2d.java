package thkoeln.st.st2praktikum.exercise;

public class Vector2d {
    private int x=0,y=0;

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

    public Vector2d() {}
    public Vector2d(int x, int y)
    {
        this.x=x;
        this.y=y;
    }
}
