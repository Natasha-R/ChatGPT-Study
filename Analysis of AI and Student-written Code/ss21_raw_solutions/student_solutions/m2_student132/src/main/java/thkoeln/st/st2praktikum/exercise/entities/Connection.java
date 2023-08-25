package thkoeln.st.st2praktikum.exercise.entities;

public class Connection extends AbstractEntity {

    private final Space originSpace;
    private final Coordinate originCoordinate;
    private final Space destinationSpace;
    private final Coordinate destinationCoordinate;

    public Connection(Space originSpace, Coordinate originCoordinate, Space destinationSpace, Coordinate destinationCoordinate) {
        this.originSpace = originSpace;
        this.originCoordinate = originCoordinate;
        this.destinationSpace = destinationSpace;
        this.destinationCoordinate = destinationCoordinate;
    }

    public Space getOriginSpace() {
        return originSpace;
    }

    public Coordinate getOriginCoordinate() {
        return originCoordinate;
    }

    public Space getDestinationSpace() {
        return destinationSpace;
    }

    public Coordinate getDetinationCoordinate() {
        return destinationCoordinate;
    }
}
