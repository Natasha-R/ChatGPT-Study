package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine {

    private final UUID miningMachineId;
    private final String name;
    private Coordinate coordinate;
    private UUID fieldId;

    public MiningMachine(UUID miningMachineId, String name) {
        this.miningMachineId = miningMachineId;
        this.name = name;
    }

    public void moveNorth() {
        coordinate.setY(coordinate.getY()+1);
    }

    public void moveEast() {
        coordinate.setX(coordinate.getX()+1);
    }

    public void moveSouth() {
        coordinate.setY(coordinate.getY()-1);
    }

    public void moveWest() {
        coordinate.setX(coordinate.getX()-1);
    }

    public void setFieldId(UUID fieldId) {
        this.fieldId = fieldId;
    }

    public UUID getMiningMachineId() {
        return miningMachineId;
    }

    public UUID getFieldId() {
        return fieldId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
