package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@Getter
@Embeddable
@Setter
@EqualsAndHashCode
public class Point {

    private Integer x;
    private Integer y;

    public Point(){}

    public Point(Integer x, Integer y) {
        if(x>=0 && y>=0) {
            this.x = x;
            this.y = y;
        }else{
            throw new RuntimeException("Negative numbers not allowed here");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        if(pointString.matches("\\({1}\\d+\\,\\d+\\){1}")){
            String[] parts = pointString.split(",");

            int first = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
            int second = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));

            return new Point(first,second);
        }else{
            throw new RuntimeException("Wrong Syntax or negative number");
        }

    }
    
}
