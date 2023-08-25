package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.converter.StringConverter;
import thkoeln.st.st2praktikum.exercise.map.barrier.BarrierException;
import thkoeln.st.st2praktikum.exercise.map.Locatable;
import thkoeln.st.st2praktikum.exercise.map.connection.Orientation;

public class Barrier {

    private final Locatable start;
    private final Locatable end;
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
    public Barrier(String barrierString ) {
        StringConverter convert = new StringConverter();
        String[] barrierPoints = convert.splitBarrierString( barrierString );
        this.start = new Point( barrierPoints[ 0 ]);
        this.end = new Point( barrierPoints[ 1 ]);
        this.orientation = determineOrientation();
    }

    public Orientation determineOrientation(){
        if ( start.getX() == end.getX()  && start.getY() != end.getY()  ) {
            return Orientation.VERTICAL;
        } else if ( start.getX() != end.getX() && start.getY() == end.getY() ) {
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

    public Locatable getStart() {
        return start;
    }

    public Locatable getEnd() {
        return end;
    }
}
