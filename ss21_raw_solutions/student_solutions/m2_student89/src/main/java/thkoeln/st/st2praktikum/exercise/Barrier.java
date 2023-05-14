package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Pattern;

public class Barrier {

    private Vector2D start;
    private Vector2D end;


    public Barrier(Vector2D pos1, Vector2D pos2) {


        if ((pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
                || (pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY())
        ) {
            throw new RuntimeException("Barrieren müssen horizontal oder Vertikal verlaufen");
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
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        if (barrierString == null) {
            throw new UnsupportedOperationException();
        }

        if (!barrierString.matches("\\(\\d,\\d\\)-\\(\\d,\\d\\)")) {
            throw new RuntimeException("Falscher Eingabestring");

        }

        String[] parts = barrierString.split(Pattern.quote("-"));
        Vector2D startcheck = new Vector2D(parts[0]);
        Vector2D endcheck = new Vector2D(parts[1]);

        if ((startcheck.getX() != endcheck.getX() && startcheck.getY() != endcheck.getY())
                || (startcheck.getX() == endcheck.getX() && startcheck.getY() == endcheck.getY())
        ) {
            throw new RuntimeException("Barrieren müssen horizontal oder Vertikal verlaufen");
        }


        if (endcheck.getX() < startcheck.getX() || endcheck.getY() < startcheck.getY()) {
           this.start=endcheck;
           this.end=startcheck;
        }

        this.start = startcheck;
        this.end = endcheck;

    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
