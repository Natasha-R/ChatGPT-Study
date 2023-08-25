package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TransportTechnology {
    @Id
    private UUID id;
    private String name;
    @ElementCollection
    private List<Connection> connection = new ArrayList<>();

    public TransportTechnology() {
    }

    public TransportTechnology(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Connection> getConnection() {
        return connection;
    }
}
