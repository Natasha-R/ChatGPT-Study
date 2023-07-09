package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    private Point start;
    private Point end;


    public Barrier (Point pos1, Point pos2) {
        this.isDiagonal(pos1, pos2);
        this.sortPoints(pos1, pos2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier (String barrierString) {
        String[] splittedString = barrierString.split("-");

        Point pos1 = new Point(splittedString[0]);
        Point pos2 = new Point(splittedString[1]);

        this.isDiagonal(pos1, pos2);
        this.sortPoints(pos1, pos2);
    }


    public void sortPoints (Point pos1, Point pos2) {
        if (pos1.getX() < pos2.getX() || pos1.getY() < pos2.getY()) {
            this.start = pos1;
            this.end = pos2;
        } else {
            this.start = pos2;
            this.end = pos1;
        }
    }


    public Boolean isVertical () {
        if (this.start.getX() == this.end.getX())
            return true;
        return false;
    }


    public void isDiagonal (Point pos1, Point pos2) {
        if (pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY()) 
            throw new UnsupportedOperationException();
    }


    public Point getStart () {
        return start;
    }


    public Point getEnd () {
        return end;
    }
}
