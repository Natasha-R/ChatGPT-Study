package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Exception.WrongWallException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.PlumbLine;

import javax.persistence.Embeddable;
import javax.persistence.Entity;


@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Wall {

    private Coordinate start;
    private Coordinate end;
    private PlumbLine plumbLine;


    public Wall(Coordinate start, Coordinate end) {
        if (start.getY() == end.getY()) {
            this.plumbLine = PlumbLine.HORIZONTAL;
            if (start.getX() < end.getX()) {
                this.start = start;
                this.end = end;
            } else {
                this.start = end;
                this.end = start;
            }
        } else if (start.getX() == end.getX()) {
            this.plumbLine = PlumbLine.VERTICAL;
            if (start.getY() < end.getY()) {
                this.start = start;
                this.end = end;
            } else {
                this.start = end;
                this.end = start;
            }

        }else{
            throw new WrongWallException(start.toString() + end.toString());
        }
    }

    public Coordinate getStart() {
        return this.start;
    }

    public Coordinate getEnd() {
        return this.end;
    }


    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        String[] wallItem = wallString.split("-");

        return new Wall(Coordinate.fromString(wallItem[0]), Coordinate.fromString(wallItem[1]));

    }


}

