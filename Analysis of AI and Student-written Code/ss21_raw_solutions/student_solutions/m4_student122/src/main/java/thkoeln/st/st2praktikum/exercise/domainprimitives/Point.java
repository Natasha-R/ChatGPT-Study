package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Point {

    @Column(insertable = false,updatable = false)
    private Integer x;
    @Column(insertable = false,updatable = false)
    private Integer y;

    public Point(Integer x, Integer y) {

        if(x<0 || y<0) throw new RuntimeException();
        this.x = x;
        this.y = y;
    }

    public Point() {

    }

    public Point(String pointString) {
        this.x = fromString(pointString).x;
        this.y = fromString(pointString).y;
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

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        if(!pointString.contains("(") || !pointString.contains(")")) throw new RuntimeException();

        pointString = pointString.replace("(","").replace(")","");
        String[] pointsAsStringArray = pointString.split(",");
        List<Integer> points = new ArrayList<>();
        for(String point : pointsAsStringArray) points.add(Integer.parseInt(point));


        if (points.size() != 2) throw new RuntimeException();

        return new Point(points.get(0),points.get(1));
    }
    
}
