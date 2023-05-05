package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Negative points aren't allowed!");
        this.x = x;
        this.y = y;
    }

    public int distToOrigin(){
        return (int)Math.sqrt(Math.pow(this.x,2) + Math.pow(this.y,2));
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x){
        this.x = x;
    }
    public void setY(Integer y){
        this.y = y;
    }


    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        int x;
        int y;
        String[] coordinates = pointString.replaceAll("[()]","").split(",");
        if (coordinates.length != 2)
            throw new IllegalArgumentException("Illegal amount of parameters!");
        try{
            x = Integer.parseInt(coordinates[0]);
            y = Integer.parseInt(coordinates[1]);
            if (x < 0 || y < 0)
                throw new IllegalArgumentException("Points have to be positive!");
        } catch (Exception exp){
            throw exp;
        }
        return new Point(x,y);
    }
    
}
