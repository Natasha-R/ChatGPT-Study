package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Connection {
    @Id
    private UUID uuid = UUID.randomUUID();
    @Embedded
    private Point sourcePoint;
    @Embedded
    private Point destinationPoint;

    @ManyToOne
    private TransportSystem transportSystem;

    private UUID sourceFieldId;
    private UUID destinationFieldId;

    protected Connection() {
    }

    public Connection(TransportSystem transportSystem, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) throws RuntimeException {
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldId = destinationFieldId;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
    }
}
