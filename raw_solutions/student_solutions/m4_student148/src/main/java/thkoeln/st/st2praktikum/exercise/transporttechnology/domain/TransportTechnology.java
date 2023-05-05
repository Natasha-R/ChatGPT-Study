package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
public class TransportTechnology {
    @Id
    private final UUID id = UUID.randomUUID();

    private String technology;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private final List<Connection> connections = new ArrayList<>();

    public TransportTechnology(String technology) {
        this.technology = technology;
    }

    public ArrayList<Connection> getConnections() {
        return (ArrayList<Connection>) connections;
    }

    protected TransportTechnology() {
    }

    public void setConnections(Connection connection) {
        connections.add(connection);
    }

}
