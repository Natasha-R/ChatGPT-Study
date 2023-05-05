package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import thkoeln.st.st2praktikum.exercise.Exception.ConnectionException;

import java.util.UUID;


public class Connection {
    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    @Getter
    private Space sourceSpace, destinationSpace;

    @Getter
    private Vector2D entry, exit;

    public Connection(Space sourceSpace, Vector2D entry, Space destinationSpace, Vector2D exit) {
        if (sourceSpace == null || destinationSpace == null)
            throw new ConnectionException("Start- und/oder Ziel-Space existiert nicht");

        if (sourceSpace.getTile(entry) == null ||
            destinationSpace.getTile(exit) == null)
            throw new ConnectionException("Start- und/oder Ziel-Tile sind nicht erreichbar");

        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.entry = entry;
        this.exit = exit;
    }
}
