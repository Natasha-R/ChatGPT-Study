package thkoeln.st.st2praktikum.exercise.World.Obstacle;

import thkoeln.st.st2praktikum.exercise.World.Movable.MovementDirectionEnum;
import thkoeln.st.st2praktikum.exercise.World.Point;

import java.util.UUID;

public class Obstacle {

    private final Point startPoint;
    private final Point endPoint;

    public Obstacle(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public boolean isVertical()
    {
        return this.startPoint.getX() == this.endPoint.getX();
    }

    public boolean isVectorColliding(Point current, Point next)
    {
        if(!this.isVertical())
        {
            if(current.getY() < next.getY() && this.endPoint.getY() == next.getY()) {
                if(next.getX() >= this.startPoint.getX() && next.getX() < this.endPoint.getX())
                {
                    return true;
                }
                System.out.println(String.format("A Colliding on %s, next pos %s", this, next));
            }

            if(current.getY() > next.getY() && this.endPoint.getY() == current.getY()) {
                if(current.getX() >= this.startPoint.getX() && current.getX() < this.endPoint.getX())
                {
                    return true;
                }
                System.out.println(String.format("A Colliding on %s, next pos %s", this, next));
            }

            return false;
        }

        if(this.isVertical())
        {
            if(current.getX() < next.getX() && this.endPoint.getX() == next.getX()) {
                if(next.getY() >= this.startPoint.getY() && next.getY() < this.endPoint.getY())
                {
                    return true;
                }
                System.out.println(String.format("A Colliding on %s, next pos %s", this, next));
            }

            if(current.getX() > next.getX() && this.endPoint.getX() == current.getX()) {
                if(current.getY() >= this.startPoint.getY() && current.getY() < this.endPoint.getY())
                {
                    return true;
                }
                System.out.println(String.format("A Colliding on %s, next pos %s", this, next));
            }

            return false;
        }

        return false;
//       Idee um das etwas eleganter zu lösen:
//        int dxc = somePoint.getX() - startPoint.getX();
//        int dyc = somePoint.getY() - startPoint.getY();
//
//        int dxl = endPoint.getX() - startPoint.getX();
//        int dyl = endPoint.getY() - startPoint.getY();
//
//        int crosses = dxc * dyl - dyc * dxl;
//
//        return 0 == crosses;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public String toString()
    {
        return String.format("starting at %s ending at %s", this.startPoint.toString(), this.endPoint.toString());
    }
}
