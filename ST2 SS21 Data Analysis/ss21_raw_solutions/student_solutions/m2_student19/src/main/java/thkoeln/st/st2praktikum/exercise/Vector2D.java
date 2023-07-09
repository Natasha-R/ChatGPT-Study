package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Vector2D {

    private Integer x;
    private Integer y;

    public Vector2D() {}

    public Vector2D(Vector2D vector2D) {
        this.x = vector2D.getX();
        this.y = vector2D.getY();
    }

    public Vector2D(Integer x, Integer y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Vector2D can't be negative.");

        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if (!vector2DString.matches("^\\([0-9]+,[0-9]+\\)$"))
            throw new IllegalArgumentException("Vector2DString doesn't match correct format. String was: " + vector2DString);

        String[] coordinates = vector2DString.split(",");
        this.x = Integer.parseInt(coordinates[0].substring(1));
        this.y = Integer.parseInt(coordinates[1].substring(0, coordinates[1].length() - 1));
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
