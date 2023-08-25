package thkoeln.st.st2praktikum.exercise;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Connection {
    @Id
    @Generated
    private UUID id;
    private UUID sourceId, destinationId;

    @ElementCollection( targetClass = Coordinate.class, fetch = FetchType.EAGER)
    private List<Coordinate> coordinates = new ArrayList<>();

    public Connection(UUID sourceRoomId, Coordinate sourceRoomCoordinate,
                      UUID destinationRoomId, Coordinate destinationRoomCoordinate) {
        id = UUID.randomUUID();
        this.sourceId = sourceRoomId;
        this.destinationId = destinationRoomId;

        coordinates = new ArrayList<>();
        coordinates.add(sourceRoomCoordinate);
        coordinates.add(destinationRoomCoordinate);
    }

    public Coordinate getSourceCoordinate() {
        return coordinates.get(0);
    }

    public Coordinate getDestinationCoordinate() {
        return coordinates.get(1);
    }
}
