package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Vector2D {

    private Integer x;
    private Integer y;

    protected Vector2D() {}
    public Vector2D(Integer x, Integer y) {
        if (x<0 || y<0) throw new IllegalArgumentException("Vector2d variables cant be Negative" );
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if(vector2DString.matches("^\\([0-9]+,[0-9]+\\)$")) {
            String[] coordinates = vector2DString.split(",");
            this.x = Integer.parseInt(coordinates[0].substring(1));
            this.y = Integer.parseInt(coordinates[1].substring(0, coordinates[1].length() - 1 ));
        }
        else throw new IllegalArgumentException("Vector2DString did not match Format. String: " + vector2DString);
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
