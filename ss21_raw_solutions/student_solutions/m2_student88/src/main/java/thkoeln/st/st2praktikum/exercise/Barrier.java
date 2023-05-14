package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    private Coordinate start;
    private Coordinate end;


    public Barrier(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        throw new UnsupportedOperationException();
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
