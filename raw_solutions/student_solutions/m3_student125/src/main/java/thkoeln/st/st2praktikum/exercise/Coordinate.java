package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidCoordinateFormatException;
import thkoeln.st.st2praktikum.exercise.exceptions.NegativeCoordinateException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Coordinate
{
    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y)
    {
        if (x < 0 || y < 0)
        {
            throw new NegativeCoordinateException("Negative Coordinate values are not allowed!", new RuntimeException());
        }
        else
        {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString)
    {
        if (coordinateString.split(",").length > 2)
        {
            throw new InvalidCoordinateFormatException("You cannot parse more or less than two coordinates!", new RuntimeException());
        }

        String strX = coordinateString.split(",")[0].replace("(", "");
        String strY = coordinateString.split(",")[1].replace(")", "");

        if (checkCoordinateStrings(strX, strY))
        {
            this.x = Integer.parseInt(strX);
            this.y = Integer.parseInt(strY);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public boolean checkCoordinateStrings(String strX, String strY)
    {
        Integer x, y;

        try
        {
            x = Integer.parseInt(strX);
            y = Integer.parseInt(strY);
        }
        catch (NumberFormatException ex)
        {
            throw new InvalidCoordinateFormatException("The given coordinate has a invalid format. Please use the pattern (x,y) to parse coordinates!", new RuntimeException());
        }

        if (x < 0 || y < 0)
        {
            throw new NegativeCoordinateException("Negative Coordinate values are not allowed!", new RuntimeException());
        }

        return true;
    }
}
