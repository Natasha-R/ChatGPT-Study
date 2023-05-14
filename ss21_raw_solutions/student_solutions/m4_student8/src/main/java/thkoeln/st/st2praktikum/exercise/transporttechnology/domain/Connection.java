package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Positionable;

import javax.persistence.Embedded;
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

    @Embedded
    private Coordinate sourceRoomCoordinate;

    @Embedded
    private Coordinate destinationRoomCoordinate;

    private UUID sourceRoom;

    private UUID destinationRoom;

    private UUID transportTechnologyID;

    public Connection(UUID sourceRoom, Coordinate sourceCoordinate, UUID destinationRoom, Coordinate destinationCoordinate,UUID transportTechnologyID) throws RuntimeException
    {

        this.connectionID = UUID.randomUUID();

        this.sourceRoom = sourceRoom;

        this.destinationRoom = destinationRoom;

        this.sourceRoomCoordinate = sourceCoordinate;

        this.destinationRoomCoordinate = destinationCoordinate;

        this.transportTechnologyID = transportTechnologyID;
    }

    @Override
    public UUID identify()
    {
        return connectionID;
    }

    @Override
    public UUID showConnectedDestinationRoom()
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

    @Override
    public UUID getDestationRoomID() {
        return destinationRoom;
    }

    private void checkInBoundaries(Coordinate coordinate,int boundaryX,int boundaryY) throws RuntimeException
    {
        if (coordinate.getX() > boundaryX || coordinate.getX() < 0)
            throw new RuntimeException();

        if (coordinate.getY() > boundaryY || coordinate.getY() < 0)
            throw new RuntimeException();
    }

}
