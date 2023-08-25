package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.FieldComponent.*;

public interface IField extends IComponent {
    void addWall(IWall wall);
    boolean addMachine(IMachine machine);
    void removeMachine(IMachine machine);
    IMachine getMiningMachineAtPosition(int position_x, int position_y);
    Boolean isTargetWithinFieldLimits(int targetX, int targetY);
    Boolean isStepClear(int stepStartX, int stepStartY, int stepEndX, int stepEndY);
}
