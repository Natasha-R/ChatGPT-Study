package thkoeln.st.st2praktikum.exercise.World;

import thkoeln.st.st2praktikum.exercise.World.Movable.MovementDirectionEnum;

public class Point implements IVector2D {

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

    public String toString()
    {
        return String.format("(%d,%d)", this.getX(), this.getY());
    }

    public boolean equals(Point point) {
        return point.getX() == this.getX() && point.getY() == this.getY();
    }

    public void copy(Point point) {
        this.setX(point.getX());
        this.setY(point.getY());
    }

    public void moveOneIn(MovementDirectionEnum direction) {
        switch (direction) {
            case no: // oben
                this.addToY(1);
                break;
            case so: // unten
                this.addToY(-1);
                break;
            case ea: // rechts
                this.addToX(1);
                break;
            case we: // links
                this.addToX(-1);
                break;
        }
    }
}
