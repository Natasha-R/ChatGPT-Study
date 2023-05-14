package thkoeln.st.st2praktikum.exercise.Space;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import java.util.UUID;


public class Connection {
    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    @Getter
    private Space sourceSpace, destinationSpace;

    @Getter
    private Coordinate entry, exit;

    public Connection(Space sourceSpace, Coordinate entry, Space destinationSpace, Coordinate exit) {
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.entry = entry;
        this.exit = exit;
    }
}
