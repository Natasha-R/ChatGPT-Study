package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class MiningMachine {
    @Id
    private UUID miningMachineID;
    private String Name;
    private UUID fieldId;
    private Coordinate coordinate;
    @ElementCollection(targetClass = Task.class)
    private List<Task> tasks;

    public MiningMachine() {
    }

    public MiningMachine(UUID id, String name, Coordinate coordinate, UUID fieldId, List<Task> tasks) {
        this.Name = name;
        this.coordinate = coordinate;
        this.miningMachineID = id;
        this.fieldId = fieldId;
        this.tasks = tasks;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setFieldId(UUID fieldId) {
        this.fieldId = fieldId;
    }

    public void setMiningMachineID(UUID miningMachineID) {
        this.miningMachineID = miningMachineID;
    }
}

