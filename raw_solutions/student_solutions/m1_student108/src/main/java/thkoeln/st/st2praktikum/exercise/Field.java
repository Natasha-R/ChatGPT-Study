package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.FieldComponent.IMachine;
import thkoeln.st.st2praktikum.exercise.FieldComponent.IWall;
import thkoeln.st.st2praktikum.exercise.FieldComponent.MiningMachine;

import java.util.ArrayList;
import java.util.UUID;

public class Field implements IField {
    private final ArrayList<IWall> walls;
    private final Integer sizeX;
    private final Integer sizeY;
    @Getter
    private final UUID uuid;
    private ArrayList<IMachine> machinesOnField;

    public Field(Integer sizeX, Integer sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        walls = new ArrayList<>();
        machinesOnField = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public void addWall(IWall wall) {
        walls.add(wall);
    }

    @Override
    public boolean addMachine(IMachine machine) {
        if (!machinesOnField.contains(machine) && getMiningMachineAtPosition(0, 0) == null) {
            machinesOnField.add(machine);
            return true;
        }
        return false;
    }

    @Override
    public void removeMachine(IMachine machine) {
        machinesOnField.remove(machine);
    }

    @Override
    public IMachine getMiningMachineAtPosition(int position_x, int position_y) {
        for (int index=0; index<machinesOnField.size(); index++) {
            if (machinesOnField.get(index).getPosition()[0] == position_x && machinesOnField.get(index).getPosition()[1] == position_y) {
                return machinesOnField.get(index);
            }
        }
        return null;
    }

    @Override
    public Boolean isTargetWithinFieldLimits(int targetX, int targetY) {
        if (targetX > this.sizeX || targetX < 0 || targetY > this.sizeY || targetY < 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean isStepClear(int stepStartX, int stepStartY, int stepEndX, int stepEndY) {
        if (!this.isTargetWithinFieldLimits(stepEndX, stepEndY)) { return false; }
        for (int index=0; index<walls.size(); index++) {
            if (walls.get(index).isStepBlocked(stepStartX, stepStartY, stepEndX, stepEndY) || getMiningMachineAtPosition(stepEndX, stepEndY) != null) {
                return false;
            }
        }
        return true;
    }
}
