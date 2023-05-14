package thkoeln.st.st2praktikum.exercise.domains;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
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
    private String sourceCoordinate;
    private UUID destinationRoomId;
    private String destinationCoordinate;

    public Connection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }
}
