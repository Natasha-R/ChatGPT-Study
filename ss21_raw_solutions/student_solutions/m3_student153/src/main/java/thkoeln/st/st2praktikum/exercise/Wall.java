package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Vector2D start;
    private Vector2D end;


    public Wall(Vector2D pos1, Vector2D pos2) {
        this.start = pos1;
        this.end = pos2;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        throw new UnsupportedOperationException();
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
