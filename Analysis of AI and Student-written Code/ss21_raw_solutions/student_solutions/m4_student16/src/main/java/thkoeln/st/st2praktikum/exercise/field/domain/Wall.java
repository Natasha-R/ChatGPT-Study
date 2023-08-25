package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

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


    public Wall(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        if (start.getX() + start.getY() > end.getX() + end.getY()) {
            this.start = end;
            this.end = start;
        }
        if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY())) throw new IllegalArgumentException();
    }

    protected Wall() {}
    
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        return new Wall(Coordinate.fromString(wallString.substring(0, wallString.lastIndexOf('-'))),
                        Coordinate.fromString(wallString.substring(wallString.lastIndexOf('-') + 1)));
    }

    public boolean wallIsHorizontal() {
        return start.getY().equals(end.getY());
    }

    public boolean wallIsVertical() {
        return getStart().getX().equals(getEnd().getX());
    }

    public boolean isNorthOf(Coordinate coordinate) {
        return wallIsHorizontal() && coordinate.getY() == start.getY() - 1 && coordinate.getX() >= start.getX() && coordinate.getX() < end.getX();
    }

    public boolean isSouthOf(Coordinate coordinate) {
        return wallIsHorizontal() && coordinate.getY() == start.getY() + 1 && coordinate.getX() >= start.getX() && coordinate.getX() < end.getX();
    }

    public boolean isWestOf(Coordinate coordinate) {
        return wallIsVertical() && coordinate.getX() == start.getX() + 1 && coordinate.getY() >= start.getY() && coordinate.getY() < end.getY();
    }

    public boolean isEastOf(Coordinate coordinate) {
        return wallIsVertical() && coordinate.getX() == start.getX() - 1 && coordinate.getY() >= start.getY() && coordinate.getY() < end.getY();
    }
}
