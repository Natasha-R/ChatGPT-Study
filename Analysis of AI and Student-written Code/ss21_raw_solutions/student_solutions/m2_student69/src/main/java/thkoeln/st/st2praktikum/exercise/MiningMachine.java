package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine {

    private UUID miningMachineID;
    private String Name;
    private Field field;
    private Coordinate coordinate;

    public MiningMachine(UUID id, String name, Coordinate coordinate, Field field) {
        this.Name = name;
        this.coordinate = coordinate;
        this.miningMachineID = id;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public UUID getMiningMachine_ID() {
        return miningMachineID;
    }

    public String getName() {
        return Name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
