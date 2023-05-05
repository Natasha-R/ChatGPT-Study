package thkoeln.st.st2praktikum.exercise;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

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
