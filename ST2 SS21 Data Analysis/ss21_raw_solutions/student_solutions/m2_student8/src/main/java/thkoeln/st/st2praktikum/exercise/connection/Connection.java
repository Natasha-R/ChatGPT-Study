package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Positionable;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.Roomable;

import java.util.UUID;

public class Connection implements Connectable
{
    private UUID connectionID;

    private Coordinate sourceRoomCoordinate;

    private Coordinate destinationRoomCoordinate;

    private Room sourceRoom;

    private Room destinationRoom;

    public Connection(Roomable sourceRoom, Coordinate sourceCoordinate, Roomable destinationRoom, Coordinate destinationCoordinate) throws RuntimeException
    {

        checkInBoundaries(sourceCoordinate,((Room)sourceRoom).getWidth(),((Room)sourceRoom).getHeight());

        checkInBoundaries(destinationCoordinate,((Room)destinationRoom).getWidth(),((Room)destinationRoom).getHeight());

        this.connectionID = UUID.randomUUID();

        this.sourceRoom = (Room) sourceRoom;

        this.destinationRoom = (Room) destinationRoom;

        this.sourceRoomCoordinate = sourceCoordinate;

        this.destinationRoomCoordinate = destinationCoordinate;
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

    private void checkInBoundaries(Coordinate coordinate,int boundaryX,int boundaryY) throws RuntimeException
    {
       if (coordinate.getCoordinateX() > boundaryX || coordinate.getCoordinateX() < 0)
           throw new RuntimeException();

       if (coordinate.getCoordinateY() > boundaryY || coordinate.getCoordinateY() < 0)
           throw new RuntimeException();
    }


}
