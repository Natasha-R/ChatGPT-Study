package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

import java.util.UUID;

public class Connection implements  Connectable{
    private final UUID connectionId;
    private final Coordinate sourcePosition;
    private final Coordinate destinationPosition;
    private UUID sourceSpaceId;
    private UUID destinationSpaceID;


    public Connection(UUID sourceSpaceId,Coordinate sourcePosition, UUID destinationSpaceID, Coordinate destinationPosition){





        this.connectionId=UUID.randomUUID();
        this.sourceSpaceId=sourceSpaceId;
        this.sourcePosition=sourcePosition;
        this.destinationSpaceID=destinationSpaceID;
        this.destinationPosition=destinationPosition;
    }
    @Override
    public Coordinate getSourcePosition() { return this.sourcePosition; }

    @Override
    public Coordinate getDestinationPosition() { return this.destinationPosition; }

    @Override
    public UUID getId() {
        return this.connectionId;
    }
    @Override
    public UUID getSourceSpaceId() { return sourceSpaceId; }
    @Override
    public UUID getDestinationSpaceID() { return destinationSpaceID; }
}
