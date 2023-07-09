package thkoeln.st.st2praktikum.exercise;



import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

// Value Object
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Access(AccessType.FIELD)
public class Rectangle {

    Coordinate coordinate;
    Dimension dimension;

    static Rectangle fromCoordinates(Coordinate start, Coordinate end){
        return new Rectangle(
                start,
                new Dimension(
                        end.getX() - start.getX(),
                        end.getY() - start.getY()
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

}
