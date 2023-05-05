package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Vector2D
{

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y)
    {
        if(x < 0 || y < 0)
            throw new Vector2DException();

        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString)
    {
        String[] vector2D = vector2DString.substring(1, vector2DString.length()-1).split(",");

        if (vector2DString.charAt(0) != '(' || vector2DString.charAt(vector2DString.length()-1) != ')')
        {
            throw new Vector2DException();
        }
        if (vector2D.length != 2)
        {
            throw new Vector2DException();
        }

        try
        {
            x = Integer.parseInt(vector2D[0]);
            y = Integer.parseInt(vector2D[1]);
        }
        catch(NumberFormatException e)
        {
            throw new Vector2DException();
        }

        if(x < 0 || y < 0)
            throw new Vector2DException();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Vector2D that = (Vector2D) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
