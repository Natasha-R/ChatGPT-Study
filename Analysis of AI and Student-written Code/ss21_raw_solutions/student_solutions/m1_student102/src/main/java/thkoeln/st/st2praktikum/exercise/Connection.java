package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private final UUID connectionID;
    private final String  source;
    private final String destination;
    private final UUID idSource;
    private final UUID idDestination;

    public UUID getIdSource() {
        return idSource;
    }

    public UUID getIdDestination() {
        return idDestination;
    }
    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Connection( String source, String destination, UUID idSource,UUID idDestination) {
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