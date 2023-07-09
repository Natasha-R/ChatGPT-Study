package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Barrier {
    CheckCorrectString check = new CheckCorrectString();
    private Point start;
    private Point end;


    public Barrier(Point pos1, Point pos2) {

        Point swap = pos1;
        if (pos1.getX() + pos1.getY() > pos2.getX() + pos2.getY()) {    //im Konstruktor lassen oder extra Funktion dafür machen?
            pos1 = pos2;
            pos2 = swap;
        }
            if (!Objects.equals(pos1.getX(), pos2.getX()) && !Objects.equals(pos1.getY(), pos2.getY()))
            throw new RuntimeException("Keine Schrägen Wände");
        this.start = pos1;
        this.end = pos2;
    }


    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        check.checkCorrectString(barrierString);

        String firstPart = barrierString.substring(0, barrierString.indexOf(")") + 1);
        String secondPart = barrierString.substring(barrierString.lastIndexOf("("));
        Point firstCoord = new Point(firstPart);
        Point secondCoord = new Point(secondPart);


        if (!firstCoord.getX().equals(secondCoord.getX()) && !firstCoord.getY().equals(secondCoord.getY()))
            throw new RuntimeException("Keine schrägen Barrieren erlaubt!");

        if (firstCoord.getX() < secondCoord.getX() && firstCoord.getY().equals(secondCoord.getY())) {
            start = firstCoord;
            end = secondCoord;
        }
        if (firstCoord.getY() < secondCoord.getY() && firstCoord.getX().equals(secondCoord.getX())) {
            start = firstCoord;
            end = secondCoord;
        }

        if (firstCoord.getX() > secondCoord.getX() && firstCoord.getY().equals(secondCoord.getY())) {
            end = firstCoord;
            start = secondCoord;
        }
        if (firstCoord.getY() > secondCoord.getY() && firstCoord.getX().equals(secondCoord.getX())) {
            end = firstCoord;
            start = secondCoord;
        }
        if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY()))
            throw new RuntimeException("Keine schrägen Barrieren erlaubt!");
    }


    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
