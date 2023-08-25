package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.*;

import thkoeln.st.st2praktikum.exercise.Exception.ConnectionException;

import java.util.*;


@Entity
@Getter
public class TransportCategory {
    @Id
    private UUID id = UUID.randomUUID();

    private String name = "";

    @ManyToMany(targetEntity = Connection.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    private final List<Connection> connectionList = new ArrayList<>();

    protected TransportCategory () {}

    public TransportCategory (String name) {
        this.name = name;
    }

    public void addConnection (Connection connection) {
        connectionList.add(connection);
    }

    public Vector2D getExit (Space sourceSpace, Vector2D entry, Space destinationSpace) {

        for (Connection connection : connectionList) {
            if (connection.getSourceSpace().equals(sourceSpace) &&
                connection.getEntry().equals(entry) &&
                connection.getDestinationSpace().equals(destinationSpace)) {
                return connection.getExit();
            }
        }

        //throw new ConnectionException("Keine passende Connection gefunden");
        return null;
    }
}
