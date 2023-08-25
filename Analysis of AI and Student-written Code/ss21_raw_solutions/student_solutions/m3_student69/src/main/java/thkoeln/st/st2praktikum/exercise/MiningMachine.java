package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class MiningMachine {
    @Id
    private UUID miningMachineID;
    private String Name;
    private UUID fieldId;
    private Coordinate coordinate;

    public MiningMachine() {
    }

    public MiningMachine(UUID id, String name, Coordinate coordinate, UUID fieldId) {
        this.Name = name;
        this.coordinate = coordinate;
        this.miningMachineID = id;
        this.fieldId = fieldId;
    }

    public UUID getFieldId() {
        return fieldId;
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

    public void setName(String name) {
        Name = name;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setFieldId(UUID fieldId) {
        this.fieldId = fieldId;
    }

    public void setMiningMachineID(UUID miningMachineID) {
        this.miningMachineID = miningMachineID;
    }
}
