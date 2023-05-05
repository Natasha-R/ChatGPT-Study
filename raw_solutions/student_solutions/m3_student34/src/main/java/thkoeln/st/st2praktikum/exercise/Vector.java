package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * Vector in RR^2
 */
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PROTECTED)
public class Vector {
    private double x;
    private double y;

    protected Vector() {
        x = 0;
        y = 0;
    }

    public static Vector of(double x, double y) {
        return new Vector(x, y);
    }

    public static Vector of(Coordinate coordinate) {
        return Vector.of(coordinate.getX(), coordinate.getY());
    }

    public Vector add(Vector other) {
        return Vector.of(this.x + other.getX(), this.y + other.getY());
    }

    public Vector inverse() {
        return Vector.of(this.x * -1, this.y * -1);
    }

    public Vector subtract(Vector other) {
        return this.add(other.inverse());
    }

    public double scalarProduct(Vector other) {
        return this.x * other.getX() + this.y * other.getY();
    }

    public Vector multiply(double scalar) {
        return Vector.of(this.x * scalar, this.y * scalar);
    }

    public double absoluteValue() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
