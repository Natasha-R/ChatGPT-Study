package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Exception.Vector2dException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Vector2D {
    @Getter
    @Setter
    private Integer x, y;

    private void validate () {
        if (x < 0 || y < 0)
            throw new Vector2dException("Keine negativen Werte erlaubt");
    }

    protected Vector2D () {}

    public Vector2D(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        validate();
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if (!vector2DString.matches("^\\((-?\\d+,-?\\d+)\\)$"))
            throw new Vector2dException("Formatfehler: " + vector2DString);

        String[] axisStrings = vector2DString
                .replaceAll("^\\(|\\)$", "")
                .split(",", 2);

        this.x = Integer.parseInt(axisStrings[0]);
        this.y = Integer.parseInt(axisStrings[1]);
        validate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    public Vector2D add (Vector2D vector2D) {
        return new Vector2D(vector2D.x + x, vector2D.y + y);
    }

    public Double length () {
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer addToX (Integer value) {
        return this.x += value;
    }
    public Integer addToY (Integer value) {
        return this.y += value;
    }
}
