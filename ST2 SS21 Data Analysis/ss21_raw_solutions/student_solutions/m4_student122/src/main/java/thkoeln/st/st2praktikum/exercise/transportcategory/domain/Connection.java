package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
public class Connection {

    @Id
    private UUID id = UUID.randomUUID();
    private UUID sourceRoomId;

    @OneToOne
    private TransportCategory category;
    @Embedded
    private Point sourceCoordinate;
    private UUID destinationRoomId;
    @Embedded
    private Point destinationCoordinate;

    public Connection(UUID sourceRoomId, Point sourceCoordinate, UUID destinationRoomId, Point destinationCoordinate, TransportCategory transportCategory) {
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
        this.category = transportCategory;
    }

    public Connection() {

    }
}
