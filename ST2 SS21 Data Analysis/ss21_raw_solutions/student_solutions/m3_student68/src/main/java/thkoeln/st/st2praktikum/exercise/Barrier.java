package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exception.BarrierException;

public class Barrier {

    private Coordinate start;
    private Coordinate end;

    public Barrier(Coordinate pos1, Coordinate pos2) {
        if ( pos1 == pos2)
            throw new BarrierException("Both Coordinates are equal: " + pos1 + " " + pos2);
        this.start = Coordinate.min(pos1,pos2);
        this.end = Coordinate.max(pos1,pos2);

        if(pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
            throw new BarrierException("Diagonal barriers not allowed " + pos1 + " " + pos2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        if ( !barrierString.matches("\\([0-9]+,[0-9]+\\)\\-\\([0-9]+,[0-9]+\\)") )
            throw new BarrierException("No valid Barrier String: " + barrierString);
        String[] separatedCoordinates = barrierString.split("-");
        Coordinate pos1 = new Coordinate(separatedCoordinates[0]);
        Coordinate pos2 = new Coordinate(separatedCoordinates[1]);

        if ( pos1 == pos2)
            throw new BarrierException("Both Coordinates are equal: " + barrierString);

        if(pos1.getX() != pos2.getX() && pos1.getY() != pos2.getY())
            throw new BarrierException("Diagonal barriers not allowed : " + barrierString);

        this.start = Coordinate.min(pos1,pos2);
        this.end = Coordinate.max(pos1,pos2);
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
