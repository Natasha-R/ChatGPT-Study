package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Pattern;

public class Wall {

    private Coordinate start;
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {

        if ((pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())||
        (pos1.getX().equals(pos2.getX()) && pos1.getY().equals(pos2.getY()))
        ) {
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
    public Wall(String wallString) {
        if (wallString == null) {
            throw new UnsupportedOperationException();
        }

        if (!wallString.matches("\\(\\d,\\d\\)-\\(\\d,\\d\\)")) {
            throw new RuntimeException("Falscher Eingabestring");
        }

        String[] parts = wallString.split(Pattern.quote("-"));
        Coordinate startcheck = new Coordinate(parts[0]);
        Coordinate endcheck = new Coordinate(parts[1]);

        if ((startcheck.getX() != endcheck.getX() && startcheck.getY() != endcheck.getY())||
        (startcheck.getX() == endcheck.getX() && startcheck.getY() == endcheck.getY())
        ) {
            throw new RuntimeException("Waende müssen horizontal oder Vertikal verlaufen");
        }


        if (endcheck.getX() < startcheck.getX() || endcheck.getY() < startcheck.getY()) {
            this.start=endcheck;
            this.end=startcheck;
        }

        this.start = startcheck;
        this.end = endcheck;

    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
