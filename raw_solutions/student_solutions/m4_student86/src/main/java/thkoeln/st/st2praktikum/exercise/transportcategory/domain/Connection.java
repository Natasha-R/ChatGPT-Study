package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
public class Connection {
    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Space sourceSpace;

    @ManyToOne
    private Space destinationSpace;

    @Embedded
    @AttributeOverride(name = "x", column = @Column(name = "entryX"))
    @AttributeOverride(name = "y", column = @Column(name = "entryY"))
    private Vector2D entry;

    @Embedded
    @AttributeOverride(name = "x", column = @Column(name = "exitX"))
    @AttributeOverride(name = "y", column = @Column(name = "exitY"))
    private Vector2D exit;

    protected Connection () {}

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
