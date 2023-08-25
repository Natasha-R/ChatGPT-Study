package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine {
    private String name;
    private UUID fieldId;
    private Coordinate coordinate;

    public MiningMachine(String name) {
        this.name = name;
        this.fieldId = null;
        this.coordinate = new Coordinate(0, 0);
    }

    public MiningMachine(String name, Coordinate coordinate) {
        this.name = name;
        this.fieldId = null;
        this.coordinate = coordinate;
    }

    public MiningMachine(Coordinate coordinate, UUID uuid) {
        this.coordinate = coordinate;
        this.fieldId = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getFieldId() {
        return fieldId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
