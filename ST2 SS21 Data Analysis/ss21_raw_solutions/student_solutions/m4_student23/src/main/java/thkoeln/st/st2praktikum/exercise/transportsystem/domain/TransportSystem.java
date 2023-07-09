package thkoeln.st.st2praktikum.exercise.transportsystem.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TransportSystem {

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    @Getter
    private final List<Connection> connections = new ArrayList<>();

    @Id
    private UUID uuid;
    private String name;

    protected TransportSystem() {
    }

    public TransportSystem(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public UUID addConnection(Connection connection) {
        this.connections.add(connection);
        return connection.getUUID();
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "TransportSystem{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}
