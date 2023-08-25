package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;


import javax.persistence.*;

import java.util.regex.Pattern;
@Getter
@Embeddable
public class Wall {
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_1")),
            @AttributeOverride(name = "y", column = @Column(name = "y_1"))
    })
    @Embedded
    private Coordinate start;

    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_2")),
            @AttributeOverride(name = "y", column = @Column(name = "y_2"))
    })
    @Embedded
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {

        if ((pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY()) ||
                (pos1.getX().equals(pos2.getX()) && pos1.getY().equals(pos2.getY()))) {
            throw new RuntimeException("Waende müssen horizontal oder Vertikal verlaufen");
        }

        if (pos2.getX() < pos1.getX() || pos2.getY() < pos1.getY()) {
            this.start = pos2;
            this.end = pos1;
        } else {
            this.start = pos1;
            this.end = pos2;
        }
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString){
        if (wallString == null) {
            throw new UnsupportedOperationException();
        }

        if (!wallString.matches("\\(\\d,\\d\\)-\\(\\d,\\d\\)")) {
            throw new RuntimeException("Falscher Eingabestring");

        }

        String[] parts = wallString.split(Pattern.quote("-"));
        Coordinate startcheck =  Coordinate.fromString(parts[0]);
        Coordinate endcheck =  Coordinate.fromString(parts[1]);

        if ((startcheck.getX() != endcheck.getX() && startcheck.getY() != endcheck.getY())||
        (startcheck.getX() == endcheck.getX() && startcheck.getY() == endcheck.getY())
        ) {
            throw new RuntimeException("Barrieren müssen horizontal oder Vertikal verlaufen");
        }


        if (endcheck.getX() < startcheck.getX() || endcheck.getY() < startcheck.getY()) {
            return new Wall(endcheck,startcheck);
        }

        return new Wall(startcheck, endcheck);
    }


    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}

