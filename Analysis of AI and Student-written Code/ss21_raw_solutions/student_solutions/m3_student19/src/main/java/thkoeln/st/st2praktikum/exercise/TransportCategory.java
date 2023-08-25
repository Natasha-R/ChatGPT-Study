package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class TransportCategory {

    @Id
    private UUID id;

    @Setter
    private String name;

    @OneToMany
    private List<Connection> connections = new ArrayList<>();

    public TransportCategory() {}

    public TransportCategory(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public void addConnection(Connection newConnection) {
        this.connections.add(newConnection);
    }
}
