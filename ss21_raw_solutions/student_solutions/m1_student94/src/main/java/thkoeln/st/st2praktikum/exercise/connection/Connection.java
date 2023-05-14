package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.inner.Position;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

import java.util.UUID;

public class Connection implements  Connectable{
    private final UUID connectionId;
    private final Position sourcePosition;
    private final Position destinationPosition;


    public Connection(Walkable sourceSpace, String sourcePosition,Walkable destinationSpace, String destinationPosition){
        this.connectionId=UUID.randomUUID();
        this.sourcePosition=new Position(sourceSpace,sourcePosition);
        this.destinationPosition=new Position(destinationSpace,destinationPosition);
    }
    @Override
    public Position getSourcePosition() { return this.sourcePosition; }

    @Override
    public Position getDestinationPosition() { return this.destinationPosition; }

    @Override
    public UUID getId() {
        return this.connectionId;
    }
}
