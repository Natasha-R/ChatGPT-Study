package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.ParseException;

import java.util.Objects;

/**
 * Nicht wundern: Ich nutze die neu vorgegebenen Klassen als Wrapper-Klassen, da meine Struktur bereits in M1 dem Design
 * entsprochen hat, allerdings teilweise mit erweiterter Funktion, daher ist ein einfaches Umschreiben nicht direkt
 * möglich...
 */
public class Vector2D {

    private Position position;

    public Vector2D(Integer x, Integer y) {
        this(Position.of(x, y));
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) throws ParseException {
        this(Position.of(vector2DString));
    }

    public Vector2D(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return position.toPositionString();
    }

    public Integer getX() {
        return position.getX();
    }

    public Integer getY() {
        return position.getY();
    }

    public Position getPosition() {
        return position;
    }
}
