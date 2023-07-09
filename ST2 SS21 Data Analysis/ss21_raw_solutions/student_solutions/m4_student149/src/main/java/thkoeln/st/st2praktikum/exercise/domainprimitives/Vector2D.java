package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;
import thkoeln.st.st2praktikum.exercise.exception.Vector2DOutOfSpaceException;
import thkoeln.st.st2praktikum.exercise.parser.Vector2DParser;

import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Vector2D {

    @Transient
    private static final Vector2DParser parser = new Vector2DParser();

    private Integer x;
    private Integer y;

    public Vector2D(int x, int y) throws Vector2DOutOfSpaceException {
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

    public void setX(int x) {
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Integer getY() {
        return y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public static Vector2D fromString(String vector2DString) {
        return new Vector2D(vector2DString);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

