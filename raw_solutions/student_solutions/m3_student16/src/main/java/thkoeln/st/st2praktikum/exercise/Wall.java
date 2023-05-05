package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Embeddable
@EqualsAndHashCode
public class Wall {

    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
        if (start.getX() + start.getY() > end.getX() + end.getY()) {
            this.start = pos2;
            this.end = pos1;
        }
        if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY())) throw new IllegalArgumentException();

    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        this(
                new Coordinate(wallString.substring(0, wallString.lastIndexOf('-'))),
                new Coordinate(wallString.substring(wallString.lastIndexOf('-') + 1))
        );
    }

    public Wall() {

    }

    public boolean wallIsHorizontal() {
        return start.getY().equals(end.getY());
    }

    public boolean wallIsVertical() {
        return getStart().getX().equals(getEnd().getX());
    }

    public boolean isNorthOf(Coordinate coordinate) {
        return wallIsHorizontal() && coordinate.getY() == start.getY() - 1 && coordinate.getX() > start.getX() && coordinate.getX() < end.getX();
    }

    public boolean isSouthOf(Coordinate coordinate) {
        return wallIsHorizontal() && coordinate.getY() == start.getY() + 1 && coordinate.getX() > start.getX() && coordinate.getX() < end.getX();
    }

    public boolean isWestOf(Coordinate coordinate) {
        return wallIsVertical() && coordinate.getX() == start.getX() + 1 && coordinate.getY() > start.getY() && coordinate.getY() < end.getY();
    }

    public boolean isEastOf(Coordinate coordinate) {
        return wallIsVertical() && coordinate.getX() == start.getX() - 1 && coordinate.getY() > start.getY() && coordinate.getY() < end.getY();
    }
}
