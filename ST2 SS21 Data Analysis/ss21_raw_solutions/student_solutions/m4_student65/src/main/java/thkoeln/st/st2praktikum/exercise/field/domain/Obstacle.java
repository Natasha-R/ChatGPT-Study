package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.IllegalActionException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embeddable;


@Embeddable
@NoArgsConstructor
public class Obstacle {

    private Point start;
    private Point end;


    public Obstacle(Point start, Point end) {
        
        this.start = start;
        this.end = end;
        apllyConditions();
    }
    
    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {
        boolean wrongFormat = false;
        int index = obstacleString.indexOf(")-(");
        if(index == -1)
        {
            throw new IllegalActionException("Obstaclestring has a wrong format!");
        }
        String firstCord = obstacleString.substring(0,index+1);
        String secondCord = obstacleString.substring(index+2,obstacleString.length());
        Obstacle obstacle = new Obstacle(Point.string2Point(firstCord),Point.string2Point(secondCord));
        obstacle.apllyConditions();
        return obstacle;
    }
    private void apllyConditions() {
        if (start.getX() != end.getX() && start.getY() != end.getY()) {
            throw new IllegalActionException("Obstacle has to be vertically or horizontally!");
        }
        if (start.getX() > end.getX()) {
            Point swap = new Point(start.getX(), start.getY());
            start = end;
            end = swap;
        }
        if (start.getY() > end.getY()) {
            Point swap = new Point(start.getX(), start.getY());
            start = end;
            end = swap;
        }
    }
    public static int getNumberAt(String obstacleString, int index)
    {
        char curChar = obstacleString.charAt(index);
        int size = 0;
        while(curChar >= '0' && curChar <= '9')
        {
            size++;
            curChar = obstacleString.charAt(index+size);
        }
        if(size == 0)
        {
            throw new IllegalActionException("No Number detected!");
        }
        String s = obstacleString.substring(index,index+size);
        int number = Integer.valueOf(obstacleString.substring(index,index+size));
        return number;
    }
}
