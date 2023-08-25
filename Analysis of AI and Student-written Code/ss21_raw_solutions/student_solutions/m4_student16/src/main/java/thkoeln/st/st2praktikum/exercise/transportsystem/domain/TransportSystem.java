package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.Getter;

import javax.persistence.CascadeType;
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
    private final UUID id = UUID.randomUUID();
    private String system;

    public TransportSystem(String system){
        this.system = system;
    }

    public TransportSystem() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    protected List<Connection> connections = new ArrayList<>();

    public void addConnection(Connection connection){
        connections.add(connection);
    }
}
