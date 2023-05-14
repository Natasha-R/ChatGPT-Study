package thkoeln.st.st2praktikum.exercise.Component;

import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Wall;

public interface IField extends IComponentWithUUID {
    void addWall(Wall wall);
    boolean addMachine(MiningMachine machine);
    void removeMachine(MiningMachine machine);
    MiningMachine getMiningMachineAtPosition(int position_x, int position_y);
    Boolean isTargetWithinFieldLimits(int targetX, int targetY);
    Boolean isStepClear(Point stepStart, Point stepEnd);
}
