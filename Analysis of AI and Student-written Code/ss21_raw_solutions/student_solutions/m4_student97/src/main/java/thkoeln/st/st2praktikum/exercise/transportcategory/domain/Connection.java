package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Connection {

    @Id
    @Getter
    private UUID id;
    @Getter
    @Embedded
    private Point startPosition;
    @Getter
    @OneToOne (cascade = CascadeType.ALL)
    private Room startRoom;
    @Getter
    @Embedded
    private Point endPosition;
    @Getter
    @OneToOne (cascade = CascadeType.ALL)
    private Room endRoom;


    public Connection(Point startPosition, Room startRoom, Point endPosition, Room endRoom) {
        this.id = UUID.randomUUID();
        this.startPosition = startPosition;
        this.startRoom = startRoom;
        this.endPosition = endPosition;
        this.endRoom = endRoom;
    }


    public Boolean equalsSource(UUID sourceRoom, Point sourcePosition, UUID destinationRoom) {
        return (startRoom.getId().equals(sourceRoom) && startPosition.equals(sourcePosition) && endRoom.getId().equals(destinationRoom));
    }
}
