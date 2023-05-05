package thkoeln.st.st2praktikum.exercise;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


@Embeddable
@Access(AccessType.FIELD)
public class Point implements Serializable {

    private Integer x;
    private Integer y;


    public Point() {}


    public Point(Integer x, Integer y) {

        negativeValues(x, y);

        this.x = x;
        this.y = y;
    }


    public Point(String pointString) {
        if (!pointString.matches("(\\()([\\d]+)(,)([\\d]+)(\\))"))
            throw new UnsupportedOperationException();

        String[] processedPointString = pointString.replaceAll("\\(", "").replaceAll("\\)", "").split(",");

        negativeValues(Integer.parseInt(processedPointString[0]), Integer.parseInt(processedPointString[1]));

        this.x = Integer.parseInt(processedPointString[0]);
        this.y = Integer.parseInt(processedPointString[1]);

    }


    public void negativeValues(Integer x, Integer y) {
        if (x < 0 || x < 0 || y < 0 || y < 0)
            throw new UnsupportedOperationException();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Point that = (Point) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
