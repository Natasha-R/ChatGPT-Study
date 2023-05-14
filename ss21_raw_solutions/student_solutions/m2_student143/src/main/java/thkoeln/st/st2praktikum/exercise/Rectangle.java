package thkoeln.st.st2praktikum.exercise;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Rectangle {
    private Coordinate coordinate;
    private Dimension dimension;

    static Rectangle fromCoordinates(Coordinate start, Coordinate end){
        return new Rectangle(
                start,
                new Dimension(
                        end.getY() - start.getY(),
                        end.getX() - start.getX()
                )
        );
    }

    public boolean isWithinWidth(Rectangle other){
        return this.coordinate.getX() + this.dimension.getWidth() > other.coordinate.getX()
                && this.coordinate.getX() < other.coordinate.getX() + other.dimension.getWidth();
    }

    public boolean isWithinHeight(Rectangle other){
        return this.coordinate.getY() + this.dimension.getHeight() > other.coordinate.getY()
                && this.coordinate.getY() < other.coordinate.getY() + other.dimension.getHeight();
    }

    public boolean isRightAlignedWith(Rectangle other){
        return (this.coordinate.getX() + this.dimension.getWidth() == other.coordinate.getX());
    }

    public boolean isTopAlignedWith(Rectangle other){
        return (this.coordinate.getY() + this.dimension.getHeight() == other.coordinate.getY());
    }

    public boolean isLeftAlignedWith(Rectangle other){
        return other.isRightAlignedWith(this);
    }

    public boolean isBottomAlignedWith(Rectangle other){
        return other.isTopAlignedWith(this);
    }

    public boolean isOverlapping(Rectangle other) {
        return isWithinWidth(other) && isWithinHeight(other);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "coordinate=" + coordinate +
                ", dimension=" + dimension +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(coordinate, rectangle.coordinate) && Objects.equals(dimension, rectangle.dimension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, dimension);
    }
}
