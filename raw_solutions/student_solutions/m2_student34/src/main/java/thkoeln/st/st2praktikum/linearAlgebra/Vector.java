package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Vector in RR^2
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class Vector {
    private final double x;
    private final double y;

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
