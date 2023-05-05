package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Connection implements Connectable
{
    @Id
    private UUID connectionID;

    @ManyToOne
    private Coordinate sourceRoomCoordinate;

    @ManyToOne
    private Coordinate destinationRoomCoordinate;

    @ManyToOne
    private Room sourceRoom;

    @ManyToOne
    private Room destinationRoom;

    @ManyToOne
    private TransportTechnology transportTechnology;

    public Connection(Roomable sourceRoom, Coordinate sourceCoordinate, Roomable destinationRoom, Coordinate destinationCoordinate,
                      TransportTechnology transportTechnology) throws RuntimeException
    {

        checkInBoundaries(sourceCoordinate,((Room)sourceRoom).getWidth(),((Room)sourceRoom).getHeight());

        checkInBoundaries(destinationCoordinate,((Room)destinationRoom).getWidth(),((Room)destinationRoom).getHeight());

        this.connectionID = UUID.randomUUID();

        this.sourceRoom = (Room) sourceRoom;

        this.destinationRoom = (Room) destinationRoom;

        this.sourceRoomCoordinate = sourceCoordinate;

        this.destinationRoomCoordinate = destinationCoordinate;

        this.transportTechnology = transportTechnology;
    }

    @Override
    public UUID identify()
    {
        return connectionID;
    }

    @Override
    public Roomable showConnectedDestinationRoom()
    {
        return  destinationRoom;
    }

    @Override
    public Positionable showStartConnectionPoint()
    {
        return (Positionable) sourceRoomCoordinate;
    }

    @Override
    public Positionable showDestinationCoordinate()
    {
        return (Positionable) destinationRoomCoordinate;
    }

    private void checkInBoundaries(Coordinate coordinate,int boundaryX,int boundaryY) throws RuntimeException
    {
        if (coordinate.getX() > boundaryX || coordinate.getX() < 0)
            throw new RuntimeException();

        if (coordinate.getY() > boundaryY || coordinate.getY() < 0)
            throw new RuntimeException();
    }


}
