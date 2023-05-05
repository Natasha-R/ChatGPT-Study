package thkoeln.st.st2praktikum.exercise;

import lombok.*;
import thkoeln.st.st2praktikum.StringParser.PointParser;
import thkoeln.st.st2praktikum.exercise.Exception.NegativePointCoordinateException;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.MatchResult;

@Embeddable
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Point {

    private Integer x;
    private Integer y;

    public Point(Integer x, Integer y) {
        this.throwOnInvalidCoordinate(x);
        this.throwOnInvalidCoordinate(y);

        this.x = x;
        this.y = y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        PointParser pointParser = new PointParser();
        MatchResult matchResult = pointParser.parse(pointString);

        int xCoordinate = Integer.parseInt(matchResult.group(1));
        int yCoordinate = Integer.parseInt(matchResult.group(2));

        this.throwOnInvalidCoordinate(xCoordinate);
        this.throwOnInvalidCoordinate(yCoordinate);

        this.x = xCoordinate;
        this.y = yCoordinate;
    }

    private void throwOnInvalidCoordinate(int someCoordinate)
    {
        if(someCoordinate >= 0)
        {
            return;
        }

        throw new NegativePointCoordinateException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Point that = (Point)o;
        return this.x.equals(that.x) && this.y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void removeOneFromX()
    {
        if(this.x.equals(0))
        {
            throw new NegativePointCoordinateException();
        }

        this.x--;
    }

    public void addOneToX()
    {
        this.x++;
    }

    public void removeOneFromY()
    {
        if(this.y.equals(0))
        {
            throw new NegativePointCoordinateException();
        }

        this.y--;
    }

    public void addOneToY()
    {
        this.y++;
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

    public void setX(Integer x) { this.x = x; }

    public void setY(Integer y) { this.y = y; }
}
