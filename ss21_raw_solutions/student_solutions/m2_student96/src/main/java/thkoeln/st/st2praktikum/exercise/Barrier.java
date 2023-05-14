package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.converter.StringConverter;
import thkoeln.st.st2praktikum.exercise.map.BarrierException;
import thkoeln.st.st2praktikum.exercise.map.Orientation;

public class Barrier {

    private final Point start;
    private final Point end;
    private Orientation orientation;


    public Barrier(Point pos1, Point pos2) {
        if ( pos2.getX() < pos1.getX() || pos2.getY() < pos1.getY() ) {
            this.start = pos2;
            this.end = pos1;
        } else {
            this.start = pos1;
            this.end = pos2;
        }
        this.orientation = determineOrientation();
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        StringConverter convert = new StringConverter();
        String[] barrierPoints = convert.splitBarrierString( barrierString );
        this.start = new Point( barrierPoints[ 0 ] );
        this.end = new Point( barrierPoints[ 1 ] );
        this.orientation = determineOrientation();
    }

    public Orientation determineOrientation(){
        if ( start.getX().equals( end.getX() ) && !start.getY().equals( end.getY() ) ) {
            return Orientation.VERTICAL;
        } else if ( !start.getX().equals( end.getX() ) && start.getY().equals( end.getY() ) ) {
            return  Orientation.HORIZONTAL;
        } else {
            throw new BarrierException( "This String is invalid since its nighter vertical nor horizontal." );
        }
    }

    public Boolean isVertical() {
        return orientation.equals(Orientation.VERTICAL);
    }

    public Boolean isHorizontal() {
        return orientation.equals(Orientation.HORIZONTAL);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
