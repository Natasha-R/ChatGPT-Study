package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;


@Entity
@NoArgsConstructor
public class Connection {

    @Id
    @Getter
    private UUID id;
    @ManyToOne (cascade = CascadeType.ALL)
    private TransportCategory transportCategory;
    private UUID destinationRoomId;

    @Embedded
    private Point sourcePoint;
    @Embedded
    private Point destinationPoint;


    public Connection (TransportCategory transportCategory, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        this.id = UUID.randomUUID();
        this.transportCategory = transportCategory;
        this.destinationRoomId = destinationRoomId;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
    }


    public UUID getId () {
        return this.id;
    }


    public UUID getDestinationRoomId () {
        return this.destinationRoomId;
    }


    public Point getSourcePoint () {
        return this.sourcePoint;
    }


    public Point getDestinationPoint () {
        return this.destinationPoint;
    }


    public UUID getTransportCategory () {
        return this.transportCategory.getId();
    }
}   