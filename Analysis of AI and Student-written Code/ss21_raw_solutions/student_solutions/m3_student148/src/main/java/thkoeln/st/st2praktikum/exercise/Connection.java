package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;


@Entity
public class Connection {

    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @OneToOne
    @Getter
    private TransportTechnology transportTechnology;

    @OneToOne
    @Getter
    private Field sourceField;

    @OneToOne
    @Getter
    private Field destinationField;

    @Setter
    @Getter
    @Embedded
    private Coordinate sourceCoordinate;

    @Setter
    @Getter
    @Embedded
    private Coordinate destinationCoordinate;


    public Connection(TransportTechnology transportTechnology, Field sourceFieldId, Coordinate sourceCoordinate, Field destinationFieldId, Coordinate destinationCoordinate) {
        this.transportTechnology = transportTechnology;
        this.sourceField = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationField = destinationFieldId;
        this.destinationCoordinate = destinationCoordinate;
    }

    protected Connection() {
    }

    public UUID getSourceFieldId() {
        return this.sourceField.getId();
    }

    public UUID getDestinationFieldId() {
        return this.destinationField.getId();
    }
}