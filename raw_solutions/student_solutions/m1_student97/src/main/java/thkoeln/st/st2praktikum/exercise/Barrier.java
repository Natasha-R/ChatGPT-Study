package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    protected Coordinate startCoordinate;
    protected Coordinate endCoordinate;


    public Barrier (String barrierString) {
        String[] splittedString = barrierString.split("-");
        Coordinate coordinate1 = new Coordinate(splittedString[0]);
        Coordinate coordinate2 = new Coordinate(splittedString[1]);
        
        // StartCoordinate should allways has smaller values for simplified block testing
        if (coordinate1.x < coordinate2.x || coordinate1.y < coordinate2.y) {
            this.startCoordinate = coordinate1;
            this.endCoordinate = coordinate2;
        } else {
            this.startCoordinate = coordinate2;
            this.endCoordinate = coordinate1;
        }
    }


    public Boolean isVertical () {
        if (startCoordinate.x == endCoordinate.x) return true;
        return false;
    }
}
