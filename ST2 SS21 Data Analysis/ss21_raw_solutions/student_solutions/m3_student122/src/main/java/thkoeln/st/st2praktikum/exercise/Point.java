package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
@Getter
@Setter
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

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        if(!pointString.contains("(") || !pointString.contains(")")) throw new RuntimeException();

        pointString = pointString.replace("(","").replace(")","");
        String[] pointsAsStringArray = pointString.split(",");
        List<Integer> points = new ArrayList<>();
        for(String point : pointsAsStringArray) points.add(Integer.parseInt(point));


        if (points.size() != 2) throw new RuntimeException();

        this.x = points.get(0);
        this.y = points.get(1);
    }

    public Point() {

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

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
