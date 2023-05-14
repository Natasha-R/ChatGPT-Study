package thkoeln.st.st2praktikum.exercise.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
public class SpaceConnection {
    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private TransportTechnology transportTechnology;

    @ManyToOne
    private Space source;

    @ManyToOne
    private Space destination;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="x", column= @Column(name="source_x")),
            @AttributeOverride(name="y", column= @Column(name="source_y"))
    })
    private Point sourcePoint;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="x", column= @Column(name="destination_x")),
            @AttributeOverride(name="y", column= @Column(name="destination_y"))
    })
    private Point destinationPoint;

    public SpaceConnection() {
    }
}
