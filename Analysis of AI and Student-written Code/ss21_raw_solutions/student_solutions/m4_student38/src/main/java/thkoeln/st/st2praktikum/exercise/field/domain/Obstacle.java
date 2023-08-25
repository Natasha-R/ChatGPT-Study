package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embeddable;
import java.util.Arrays;

public class Obstacle {

    private Point start;
    private Point end;


    public Obstacle(Point start, Point end) {
        if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY()))
            throw new IllegalArgumentException("Diagonal obstacles are not allowed!");

        if (start.distToOrigin() > end.distToOrigin()){
            this.start = end;
            this.end = start;
        } else{
            this.start = start;
            this.end = end;
        }
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
        Point start;
        Point end;
        if (!obstacleString.matches("^\\([^)]*\\)-\\([^)]*\\)$"))
            throw new IllegalArgumentException("Illegal format for an Obstacle-String");

        String[] locations = Arrays.toString(obstacleString.split("-")).
                replaceAll("\\s","").
                replaceAll("[\\[\\]]", "").
                replaceAll("[()]","").
                split(",");

        if (locations.length != 4)
            throw new IllegalArgumentException("Illegal amount of parameters!");

        try{
            start = new Point(Integer.parseInt(locations[0]),Integer.parseInt(locations[1]));
            end = new Point(Integer.parseInt(locations[2]),Integer.parseInt(locations[3]));
        } catch (Exception exp){
            throw exp;
        }
        return new Obstacle(start,end);
    }
    
}
