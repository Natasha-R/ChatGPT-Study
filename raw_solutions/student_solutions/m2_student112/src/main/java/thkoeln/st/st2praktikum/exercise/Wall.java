package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Point start;
    private Point end;


    public Wall(Point pos1, Point pos2) {
        this.start = pos1;
        this.end = pos2;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        throw new UnsupportedOperationException();
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
