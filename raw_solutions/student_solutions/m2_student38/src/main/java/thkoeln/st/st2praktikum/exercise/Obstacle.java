package thkoeln.st.st2praktikum.exercise;

import org.hibernate.tuple.component.PojoComponentTuplizer;

import java.util.Arrays;

public class Obstacle {

    private Point start;
    private Point end;


    public Obstacle(Point pos1, Point pos2) {
        if (pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
            throw new IllegalArgumentException("Diagonal obstacles are not allowed!");

        if (pos1.distToOrigin() > pos2.distToOrigin()){
            this.start = pos2;
            this.end = pos1;
        } else{
            this.start = pos1;
            this.end = pos2;
        }
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
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
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
