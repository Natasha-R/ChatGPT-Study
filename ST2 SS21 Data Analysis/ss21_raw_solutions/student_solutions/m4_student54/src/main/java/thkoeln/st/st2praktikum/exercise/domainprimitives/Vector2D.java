package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Validator;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y)
    {
        if(x < 0 || y < 0)
        {
            throw new IllegalArgumentException("Vectors must not be negative!");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public static Vector2D fromString(String vector2DString)
    {
        Validator validator = new Validator();

        if(!validator.isVector(vector2DString))
        {
            throw new IllegalArgumentException("This commandstring is not valid!");
        }
        return new Vector2D(validator.getVectorX(), validator.getVectorY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
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

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
