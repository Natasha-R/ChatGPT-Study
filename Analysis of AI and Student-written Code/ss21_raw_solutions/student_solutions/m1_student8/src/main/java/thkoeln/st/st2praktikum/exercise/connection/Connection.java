package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.position.Position;
import thkoeln.st.st2praktikum.exercise.position.Positionable;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.Roomable;

import java.util.UUID;

public class Connection implements Connectable
{
    private UUID connectionID;

    private Position sourceRoomCoordinate;

    private Position destinationRoomCoordinate;

    private Room sourceRoom;

    private Room destinationRoom;

    public Connection(Roomable sourceRoom, String sourceCoordinate, Roomable destinationRoom, String destinationCoordinate)
    {
        this.connectionID = UUID.randomUUID();

        this.sourceRoom = (Room) sourceRoom;

        this.sourceRoomCoordinate = new Position(sourceCoordinate);

        this.destinationRoom = (Room) destinationRoom;

        this.destinationRoomCoordinate = new Position(destinationCoordinate);
    }

    @Override
    public UUID identify()
    {
        return connectionID;
    }

    @Override
    public Roomable showConnectedDestinationRoom()
    {
        return destinationRoom;
    }

    @Override
    public Positionable showStartConnectionPoint()
    {
        return sourceRoomCoordinate;
    }

    @Override
    public Positionable showDestinationCoordinate()
    {
        return destinationRoomCoordinate;
    }
}
