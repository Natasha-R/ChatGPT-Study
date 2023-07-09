package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;
import thkoeln.st.st2praktikum.exercise.exception.Vector2DOutOfSpaceException;
import thkoeln.st.st2praktikum.exercise.parser.Vector2DParser;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class Vector2D {

    @Transient
    private static final Vector2DParser parser = new Vector2DParser();

    private Integer x;
    private Integer y;

    public Vector2D(Integer x, Integer y) throws Vector2DOutOfSpaceException {
        if (x < 0 || y < 0) {
            throw new Vector2DOutOfSpaceException(x, y);
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) throws ParseException, Vector2DOutOfSpaceException {
        this(parser.parse(vector2DString));
    }

    private Vector2D(Vector2D vector2D) {
        this(vector2D.x, vector2D.y);
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return x.equals(vector2D.x) && y.equals(vector2D.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
