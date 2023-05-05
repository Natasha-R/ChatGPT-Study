package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
