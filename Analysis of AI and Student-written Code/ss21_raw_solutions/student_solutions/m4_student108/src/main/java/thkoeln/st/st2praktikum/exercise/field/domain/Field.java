package thkoeln.st.st2praktikum.exercise.field.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Field implements IField {
    @Id
    private UUID id = UUID.randomUUID();

    @ElementCollection( targetClass = Wall.class )
    private List<Wall> walls = new ArrayList<Wall>();

    private Integer sizeX;
    private Integer sizeY;

    @OneToMany
    private List<MiningMachine> machinesOnField = new ArrayList<>();

    public Field() {}

    public Field(Integer sizeX, Integer sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public UUID getUUID() {
        return this.id;
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
            if (machinesOnField.get(index).getPosition().getX() == position_x && machinesOnField.get(index).getPosition().getY() == position_y) {
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
