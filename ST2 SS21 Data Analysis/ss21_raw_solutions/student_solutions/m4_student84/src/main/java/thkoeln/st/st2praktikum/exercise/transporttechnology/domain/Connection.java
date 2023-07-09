package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Connection {
    @Id
    @Getter
    private UUID id;
    @Getter
    private UUID sourceRoomId;
    @Getter
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="x", column = @Column(name="sourceX")),
            @AttributeOverride(name="y", column = @Column(name="sourceY"))
    })
    private Point sourceCoordinate;
    @Getter
    private UUID destinationRoomId;
    @Getter
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="x", column = @Column(name="destinationX")),
            @AttributeOverride(name="y", column = @Column(name="destinationY"))
    })
    private Point destinationCoordinate;

    public Connection(UUID sourceRoomId, Point sourceCoordinate, UUID destinationRoomId, Point destinationCoordinate) {
        this.id = UUID.randomUUID();
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }
}
