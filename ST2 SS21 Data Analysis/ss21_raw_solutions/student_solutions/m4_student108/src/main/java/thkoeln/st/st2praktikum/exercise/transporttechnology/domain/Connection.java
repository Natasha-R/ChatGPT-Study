package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.IField;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
public class Connection implements IConnection {
    @Id
    private UUID uuid = UUID.randomUUID();

    @OneToOne
    private Field sourceField;

    @Embedded
    private Point sourcefieldPosition;

    @OneToOne
    private Field destinationField;

    @Embedded
    private Point destinationPosition;

    public Connection() {}
    public Connection(IField sourceField, Point sourcePosition, IField destinationField, Point destinationPosition) {
        this.sourceField = (Field) sourceField;
        this.sourcefieldPosition = sourcePosition;
        this.destinationField = (Field) destinationField;
        this.destinationPosition = destinationPosition;
    }

    @Override
    public Boolean verifyConnection(UUID searchedSourceFieldUUID, UUID searchedDestinationFieldUUID, Point searchedSourceFieldPosition) {
        return this.getSourceField().getUUID().equals(searchedSourceFieldUUID) && this.getDestinationField().getUUID().equals(searchedDestinationFieldUUID) &&
                searchedSourceFieldPosition.equals(sourcefieldPosition);
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public MiningMachine getMachineOnSourceField() { return sourceField.getMiningMachineAtPosition(sourcefieldPosition.getX(), sourcefieldPosition.getY()); }

    @Override
    public MiningMachine getMachineOnDestinationField() { return destinationField.getMiningMachineAtPosition(destinationPosition.getX(), destinationPosition.getY()); }
}
