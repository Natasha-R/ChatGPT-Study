package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class Connection {

    private final UUID connectionID;
    private final UUID startID; //hier Feld
    private final UUID endID;
    private final Point startCoordinate; //mit Points arbeiten
    private final Point endCoordinate;

    public Connection(UUID start, Point startCoordinate, UUID end, Point endCoordinate) {
        this.connectionID = UUID.randomUUID();
        this.startID = start;
        this.endID = end;
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    public UUID getConnectionID() {
        return connectionID;
    }

    public UUID getStartID() {
        return startID;
    }

    public UUID getEndID() {
        return endID;
    }

    public Point getStartLocation() {
        return startCoordinate;
    }

    public Point getEndLocation() {
        return endCoordinate;
    }
}
