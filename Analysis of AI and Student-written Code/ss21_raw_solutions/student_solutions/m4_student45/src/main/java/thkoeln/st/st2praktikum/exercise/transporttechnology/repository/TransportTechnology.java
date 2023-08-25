package thkoeln.st.st2praktikum.exercise.transporttechnology.repository;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TransportTechnology {
    @Id
    @Generated
    private UUID id;
    private String type;

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public TransportTechnology(String type){
        id = UUID.randomUUID();
        connections = new ArrayList<>();
        this.type = type;
    }

    public Connection addConnection(Room sourceRoom, Coordinate sourceCoordinate, Room destinationRoom, Coordinate destinationCoordinate){
        Connection connection = new Connection(sourceRoom.getId(), sourceCoordinate, destinationRoom.getId(), destinationCoordinate);
        connections.add(connection);

        sourceRoom.addConnection(connection);
        destinationRoom.addConnection(connection);

        return connection;
    }
}
