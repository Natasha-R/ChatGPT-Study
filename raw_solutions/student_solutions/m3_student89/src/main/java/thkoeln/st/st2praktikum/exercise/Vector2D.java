package thkoeln.st.st2praktikum.exercise;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Objects;
import java.util.regex.Pattern;
@Embeddable
@Setter
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Vector2D  {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if (x < 0 || y < 0) {
            throw new RuntimeException("Negative Coordinate");
        }
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {

        if (vector2DString == null) {
            throw new UnsupportedOperationException();
        }
        if (!vector2DString.matches("\\(\\d,\\d\\)")) {
            throw new RuntimeException("Falscher Eingabestring");

        }

        String[] parts = vector2DString.split(Pattern.quote(","));


        int xcheck = Integer.parseInt(parts[0].replaceAll("\\D", ""));
        int ycheck = Integer.parseInt(parts[1].replaceAll("\\D", ""));
        if (xcheck < 0 || ycheck < 0) {
            throw new RuntimeException("Negative Coordinate");
        }
        this.x = xcheck;
        this.y = ycheck;


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
}
