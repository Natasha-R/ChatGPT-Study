package thkoeln.st.st2praktikum.exercise.Component;

import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Wall;

import java.util.ArrayList;
import java.util.UUID;

public class Field implements IField {
    ArrayList<Wall> walls;
    Integer sizeX;
    Integer sizeY;
    UUID uuid;
    ArrayList<MiningMachine> machinesOnField;

    public Field(Integer sizeX, Integer sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.uuid = UUID.randomUUID();
        walls = new ArrayList<>();
        machinesOnField = new ArrayList<>();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public boolean addMachine(MiningMachine machine) {
        if (!machinesOnField.contains(machine) && getMiningMachineAtPosition(0, 0) == null) {
            machinesOnField.add(machine);
            return true;
        }
        return false;
    }

    public void removeMachine(MiningMachine machine) {
        machinesOnField.remove(machine);
    }

    public MiningMachine getMiningMachineAtPosition(int position_x, int position_y) {
        for (int index=0; index<machinesOnField.size(); index++) {
            if (machinesOnField.get(index).position.getX() == position_x && machinesOnField.get(index).position.getY() == position_y) {
                return machinesOnField.get(index);
            }
        }
        return null;
    }

    public Boolean isTargetWithinFieldLimits(int targetX, int targetY) {
        if (targetX > this.sizeX || targetX < 0 || targetY > this.sizeY || targetY < 0) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean isStepClear(Point stepStart, Point stepEnd) {
        if (!this.isTargetWithinFieldLimits(stepEnd.getX(), stepEnd.getY())) { return false; }
        for (int index=0; index<walls.size(); index++) {
            if (walls.get(index).isStepBlocked(stepStart, stepEnd) || getMiningMachineAtPosition(stepEnd.getX(), stepEnd.getY()) != null) {
                return false;
            }
        }
        return true;
    }
}
