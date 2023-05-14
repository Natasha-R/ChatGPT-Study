package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mockito.internal.matchers.Null;
import thkoeln.st.st2praktikum.exercise.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransportTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Connection> connections = new ArrayList<>();

    public UUID addConnection(Connection newConnection) {
        connections.add(newConnection);
        return newConnection.getId();
    }

    public Boolean transport(TidyUpRobot tidyUpRobot, List<TidyUpRobot> robotsInTargetRoom) {
        Connection transportingConnection = connections
                .stream()
                .filter(connection -> connection.getSourceRoomId().equals(tidyUpRobot.getRoomId())
                        && connection.getSourceCoordinate().equals(tidyUpRobot.getPoint()))
                .findFirst()
                .orElse(null);

        if (transportingConnection != null) {
            return transportingConnection.transport(tidyUpRobot, robotsInTargetRoom);
        } else {
            return false;
        }
    }

    public TransportTechnology(String _name) {
        this.name = _name;
    }
}
