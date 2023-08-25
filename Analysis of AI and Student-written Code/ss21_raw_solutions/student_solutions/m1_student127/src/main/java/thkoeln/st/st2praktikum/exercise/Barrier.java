package thkoeln.st.st2praktikum.exercise;

import java.util.StringTokenizer;

//TODO: Check if input is valid.
public class Barrier {
    private final Coordinate startCoordinate;
    private final Coordinate endCoordinate;

    private String fetchAxis() {
        if (startCoordinate.getY().equals(endCoordinate.getY())) {
            return "horizontal";
        } else if (startCoordinate.getX().equals(endCoordinate.getX())) {
            return "vertical";
        } else {
            throw new IllegalStateException();
        }
    }

    private Boolean isCrossingThePlane(int plane, int current, int next) {
        return current < plane && next == plane;
    }

    public Boolean isBlocking(Coordinate currentCoordinate, Coordinate nextCoordinate) {
        //sorting to half the possible cases
        if (currentCoordinate.isGreaterThan(nextCoordinate)) {
            Coordinate temp = currentCoordinate;
            currentCoordinate = nextCoordinate;
            nextCoordinate = temp;
        }

        if ( fetchAxis().equals("horizontal") && isCrossingThePlane(startCoordinate.getY(), currentCoordinate.getY(), nextCoordinate.getY()) ) {
            return nextCoordinate.getX() >= startCoordinate.getX() && nextCoordinate.getX() < endCoordinate.getX();
        } else if ( fetchAxis().equals("vertical") && isCrossingThePlane(startCoordinate.getX(), currentCoordinate.getX(), nextCoordinate.getX()) ) {
            return nextCoordinate.getY() >= startCoordinate.getY() && nextCoordinate.getY() < endCoordinate.getY();
        }

        return false;
    }

    public Barrier(Coordinate _startCoordinate, Coordinate _endCoordinate) {
        if(_startCoordinate.isGreaterThan(_endCoordinate)) {
            this.startCoordinate = _endCoordinate;
            this.endCoordinate = _startCoordinate;
        } else {
            this.startCoordinate = _startCoordinate;
            this.endCoordinate = _endCoordinate;
        }
    }
    public Barrier(String barrierString) {
        final StringTokenizer st = new StringTokenizer(barrierString, "-");
        final Coordinate _startCoordinate = new Coordinate(st.nextToken());
        final Coordinate _endCoordinate = new Coordinate(st.nextToken());

        //TODO: Avoid the doubling of this code ... how?
        if(_startCoordinate.isGreaterThan(_endCoordinate)){
            this.startCoordinate = _endCoordinate;
            this.endCoordinate = _startCoordinate;
        } else {
            this.startCoordinate = _startCoordinate;
            this.endCoordinate = _endCoordinate;
        }
    }
}
