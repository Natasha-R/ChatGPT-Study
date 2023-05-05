package thkoeln.st.st2praktikum.exercise.Component;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.UUID;

@Getter
public class Connection implements IConnection {
    IField sourceField;
    Point sourcefieldPosition;
    IField destinationField;
    Point destinationPosition;
    UUID uuid;

    public Connection(IField sourceField, Point sourcePosition, IField destinationField, Point destinationPosition) {
        this.sourceField = sourceField;
        this.sourcefieldPosition = sourcePosition;
        this.destinationField = destinationField;
        this.destinationPosition = destinationPosition;
        this.uuid = UUID.randomUUID();
    }

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
