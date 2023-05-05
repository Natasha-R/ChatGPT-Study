package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private UUID id = UUID.randomUUID();

    private String name;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Connection> connections = new ArrayList<>();

    public UUID addConnection(Connection newConnection) {
        connections.add(newConnection);
        return newConnection.getId();
    }

    public Boolean transport(MiningMachine tidyUpRobot, List<MiningMachine> robotsInTargetRoom) {
        Connection transportingConnection = connections
                .stream()
                .filter(connection -> connection.getSourceRoomId().equals(tidyUpRobot.getFieldId())
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
