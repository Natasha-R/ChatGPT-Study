package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Connection implements Serializable {

    private UUID id = UUID.randomUUID();
    private UUID sourceRoomId;
    private Point sourceCoordinate;
    private UUID destinationRoomId;
    private Point destinationCoordinate;

    public Connection(UUID sourceRoomId, Point sourceCoordinate, UUID destinationRoomId, Point destinationCoordinate) {
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }

}
