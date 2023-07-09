package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Connection {
    @Id
    @Generated
    private UUID id;
    private UUID sourceId, destinationId;

    @Embedded
    private Coordinate sourceCoordinate;

    @Embedded
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceRoomId, Coordinate sourceRoomCoordinate,
                      UUID destinationRoomId, Coordinate destinationRoomCoordinate) {
        id = UUID.randomUUID();
        this.sourceId = sourceRoomId;
        this.destinationId = destinationRoomId;

        sourceCoordinate = sourceRoomCoordinate;
        destinationCoordinate = destinationRoomCoordinate;
    }
}
