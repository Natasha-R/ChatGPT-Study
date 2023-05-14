package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.*;
import thkoeln.st.st2praktikum.StringParser.ObstacleParser;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.domain.exception.DiagonaleObstacleException;

import javax.persistence.Embeddable;
import java.util.regex.MatchResult;

@Embeddable
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Obstacle {

    private Point start;
    private Point end;


    public Obstacle(Point start, Point end) {
        if(!start.getX().equals(end.getX()) && !start.getY().equals(end.getY()))
        {
            throw new DiagonaleObstacleException();
        }

        if(start.getX() > end.getX() || start.getY() > end.getY())
        {
            Point temporaryPoint = start;
            start = end;
            end = temporaryPoint;
        }

        this.start = start;
        this.end = end;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        ObstacleParser obstacleParser = new ObstacleParser();
        MatchResult obstacleResult = obstacleParser.parse(obstacleString);

        int x1 = Integer.parseInt(obstacleResult.group(1));
        int y1 = Integer.parseInt(obstacleResult.group(2));
        Point startPoint = new Point(x1, y1);

        int x2 = Integer.parseInt(obstacleResult.group(3));
        int y2 = Integer.parseInt(obstacleResult.group(4));
        Point endPoint = new Point(x2, y2);

        return new Obstacle(startPoint, endPoint);
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
                System.out.printf("A Colliding on %s, next pos %s%n", this, next);
            }

            if(current.getY() > next.getY() && this.end.getY().equals(current.getY())) {
                if(current.getX() >= this.start.getX() && current.getX() < this.end.getX())
                {
                    return true;
                }
                System.out.printf("A Colliding on %s, next pos %s%n", this, next);
            }

            return false;
        }

        if(this.isVertical())
        {
            if(current.getX() < next.getX() && this.end.getX().equals(next.getX())) {
                if(next.getY() >= this.start.getY() && next.getY() < this.end.getY())
                {
                    return true;
                }
                System.out.printf("A Colliding on %s, next pos %s%n", this, next);
            }

            if(current.getX() > next.getX() && this.end.getX().equals(current.getX())) {
                if(current.getY() >= this.start.getY() && current.getY() < this.end.getY())
                {
                    return true;
                }
                System.out.printf("A Colliding on %s, next pos %s%n", this, next);
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
    
}
