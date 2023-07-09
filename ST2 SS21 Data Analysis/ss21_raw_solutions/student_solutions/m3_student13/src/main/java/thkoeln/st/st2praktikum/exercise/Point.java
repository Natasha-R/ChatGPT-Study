package thkoeln.st.st2praktikum.exercise;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        if(x >= 0 && y >= 0){
            this.x = x;
            this.y = y;
        } else {
            throw new PointException("x or y is negativ");
        }
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {

        String regex = "^\\(\\d,\\d\\)$";
        boolean matches = pointString.matches(regex);

        if(matches){
            String [] coordinates = pointString
                    .substring(1,pointString.length()-1)
                    .split(",");
            this.x = Integer.parseInt(coordinates[0]);
            this.y = Integer.parseInt(coordinates[1]);

        } else {
            throw new PointException("no valid point");
        }
        //throw new UnsupportedOperationException();
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
