package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Point {

    private Integer x;
    private Integer y;

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        Point point = new Point();
        pointString = point.removeFirstAndLastCharacter(pointString);
        if(!pointString.matches("\\d+[,]+\\d"))throw new RuntimeException("point did not match format");
        String[] input = pointString.split(",");
       // point = new Point(Integer.parseInt(input[0]),Integer.parseInt(input[1]));
        point.x = Integer.parseInt(input[0]);
        point.y = Integer.parseInt(input[1]);
        return point;
    }

    public Point(Integer x, Integer y) {
        
        this.x = x;
        this.y = y;

        if(x < 0 || y < 0){
            throw new RuntimeException("negativ numbers");
        }
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

 */

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



    public String removeFirstAndLastCharacter(String S){
        StringBuilder s = new StringBuilder(S);
        s.deleteCharAt(0);
        s.deleteCharAt(s.length()-1);
        return s.toString();
    }
}
