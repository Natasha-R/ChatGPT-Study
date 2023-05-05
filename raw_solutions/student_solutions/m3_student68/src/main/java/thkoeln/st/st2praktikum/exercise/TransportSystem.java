package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.interfaces.Connectable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class TransportSystem {
    @Id
    private UUID id;
    private String system;
    @OneToMany(targetEntity = Connection.class)
    private List<Connectable> connections;

    public TransportSystem(String system) {
        this.id = UUID.randomUUID();
        this.system = system;
        this.connections = new ArrayList<>();
    }

    public void addConnection(Connectable connection) {
        connections.add(connection);
    }
}
