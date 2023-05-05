package thkoeln.st.st2praktikum.exercise.FieldComponent;

import thkoeln.st.st2praktikum.exercise.IField;

import java.util.UUID;

public class Connection implements IConnection {

    private final IField sourceField;
    private final int[] sourceFieldPosition;
    private final IField destinationField;
    private final int[] destinationFieldPosition;
    private final UUID uuid;

    public Connection(IField sourceField, int[] sourcePosition, IField destinationField, int[] destinationPosition) {
        this.sourceField = sourceField;
        this.sourceFieldPosition = new int[]{sourcePosition[0], sourcePosition[1]};
        this.destinationField = destinationField;
        this.destinationFieldPosition = new int[]{destinationPosition[0], destinationPosition[1]};
        this.uuid = UUID.randomUUID();
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public IField getDestinationField() { return this.destinationField; }

    @Override
    public int[] getDestinationFieldPosition() { return this.destinationFieldPosition; }

    @Override
    public IField getSourceField() { return this.sourceField; }

    @Override
    public IMachine getMachineOnSourceField() { return sourceField.getMiningMachineAtPosition(sourceFieldPosition[0], sourceFieldPosition[1]); }

    @Override
    public IMachine getMachineOnDestinationField() { return destinationField.getMiningMachineAtPosition(destinationFieldPosition[0], destinationFieldPosition[1]); }

}
