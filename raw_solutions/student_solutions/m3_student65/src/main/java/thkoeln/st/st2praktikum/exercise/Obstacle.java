package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class Obstacle {
    @Embedded
    private Point start;
    @Embedded
    private Point end;


    public Obstacle(Point pos1, Point pos2) {
        this.start = pos1;
        this.end = pos2;
        apllyConditions();
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        boolean wrongFormat = false;
        int index = obstacleString.indexOf(")-(");
        if(index == -1)
        {
            throw new IllegalActionException("Obstaclestring has a wrong format!");
        }
        String firstCord = obstacleString.substring(0,index+1);
        String secondCord = obstacleString.substring(index+2,obstacleString.length());

        start = Point.string2Point(firstCord);
        end = Point.string2Point(secondCord);
        apllyConditions();
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
    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    static int getNumberAt(String obstacleString, int index)
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
