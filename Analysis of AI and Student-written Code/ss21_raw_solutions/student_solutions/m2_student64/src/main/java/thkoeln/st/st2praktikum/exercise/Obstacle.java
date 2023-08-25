package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.StringParser.ObstacleParser;
import thkoeln.st.st2praktikum.exercise.Exception.DiagonaleObstacleException;

import java.util.regex.MatchResult;

public class Obstacle {

    private Point start;
    private Point end;


    public Obstacle(Point pos1, Point pos2) {
        if(this.isPointCombinationDiagonal(pos1, pos2))
        {
            throw new DiagonaleObstacleException();
        }

        this.setEndpoints(pos1, pos2);
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        ObstacleParser obstacleStringParser = new ObstacleParser();
        MatchResult obstacleMatchResult = obstacleStringParser.parse(obstacleString);

        int x1 = Integer.parseInt(obstacleMatchResult.group(1));
        int y1 = Integer.parseInt(obstacleMatchResult.group(2));
        this.start = new Point(x1, y1);

        int x2 = Integer.parseInt(obstacleMatchResult.group(3));
        int y2 = Integer.parseInt(obstacleMatchResult.group(4));
        this.end = new Point(x2, y2);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public boolean isVertical()
    {
        return this.start.getX().equals(this.end.getX());
    }

    public boolean isVectorColliding(Point current, Point next)
    {
        if(!this.isVertical())
        {
            if(current.getY() < next.getY() && this.end.getY().equals(next.getY())) {
                if(next.getX() >= this.start.getX() && next.getX() < this.end.getX())
                {
                    return true;
                }
                System.out.println(String.format("A Colliding on %s, next pos %s", this, next));
            }

            if(current.getY() > next.getY() && this.end.getY().equals(current.getY())) {
                if(current.getX() >= this.start.getX() && current.getX() < this.end.getX())
                {
                    return true;
                }
                System.out.println(String.format("A Colliding on %s, next pos %s", this, next));
            }

            return false;
        }

        if(this.isVertical())
        {
            if(current.getX() < next.getX() && this.end.getX() == next.getX()) {
                if(next.getY() >= this.start.getY() && next.getY() < this.end.getY())
                {
                    return true;
                }
                System.out.println(String.format("A Colliding on %s, next pos %s", this, next));
            }

            if(current.getX() > next.getX() && this.end.getX() == current.getX()) {
                if(current.getY() >= this.start.getY() && current.getY() < this.end.getY())
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

    public void setEndpoints(Point pos1, Point pos2)
    {
        this.start = pos1;
        this.end = pos2;

        if(this.isVertical())
        {
            if(pos2.getY() > pos1.getY())
            {
                return;
            }
        }

        if(pos2.getX() > pos1.getX())
        {
            return;
        }

        this.end = this.start;
        this.start = pos2;
    }

    private boolean isPointCombinationDiagonal(Point pos1, Point pos2) {
        return !pos1.getX().equals(pos2.getX()) && !pos1.getY().equals(pos2.getY());
    }

}
