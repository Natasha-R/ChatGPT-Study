package thkoeln.st.st2praktikum.exercise.field.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.IComponent;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

public interface IField extends IComponent {
    void addWall(Wall wall);
    boolean addMachine(MiningMachine machine);
    void removeMachine(MiningMachine machine);
    MiningMachine getMiningMachineAtPosition(int position_x, int position_y);
    Boolean isTargetWithinFieldLimits(int targetX, int targetY);
    Boolean isStepClear(Point stepStart, Point stepEnd);
}
