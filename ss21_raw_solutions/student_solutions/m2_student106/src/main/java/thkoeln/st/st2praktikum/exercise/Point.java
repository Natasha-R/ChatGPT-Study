package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class Point {

    private Integer x;
    private Integer y;

    public Point(int x, int y) throws RuntimeException {
        this.x = x;
        this.y = y;
        checkXandYareNotNegative();
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) throws RuntimeException {
        String[] pointArray =
                pointString.replace("(", "").replace(")", "").split(",");
        if (pointArray.length == 2) {
            this.x = Integer.parseInt(pointArray[0]);
            this.y = Integer.parseInt(pointArray[1]);
            checkXandYareNotNegative();
        } else throw new RuntimeException("Point consists of too many elements. Format e.g. (1,3)");
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

    public void checkXandYareNotNegative() throws RuntimeException {
        if (x < 0 || y < 0) throw new RuntimeException("Point can not have negativ values");
    }

    public Point changeNegativeValueTo0() {
        try {
            checkXandYareNotNegative();
        } catch (RuntimeException e) {
            if (x < 0) x = 0;
            if (y < 0) y = 0;
        }
        return this;
    }
}
