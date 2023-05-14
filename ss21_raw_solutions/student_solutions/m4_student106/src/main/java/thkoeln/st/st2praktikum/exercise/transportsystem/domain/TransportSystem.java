package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class TransportSystem {
    @Id
    UUID uuid = UUID.randomUUID();

    private String system;

    @OneToMany
    private final List<Connection> connections = new ArrayList<>();

    protected TransportSystem() {
    }

    public TransportSystem(String system) {
        this.system = system;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }
}
