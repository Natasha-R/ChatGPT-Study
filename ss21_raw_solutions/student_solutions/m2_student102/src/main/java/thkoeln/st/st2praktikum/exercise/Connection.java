package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private final UUID connectionID;
    private final Point  source;
    private final Point destination;
    private final UUID idSource;
    private final UUID idDestination;

    public UUID getIdSource() {
        return idSource;
    }
    public UUID getIdDestination() {
        return idDestination;
    }
    public Point getSource() {
        return source;
    }
    public Point getDestination() {
        return destination;
    }

    public Connection( Point source, Point destination, UUID idSource,UUID idDestination) {
        connectionID=UUID.randomUUID();
        this.source = source;
        this.destination = destination;
        this.idSource = idSource;
        this.idDestination = idDestination;
    }

    public UUID getConnectionID(){
        return connectionID;
    }
}
