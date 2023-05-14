package thkoeln.st.st2praktikum.exercise;

public interface IField extends IComponent {
    void addWall(Wall wall);
    boolean addMachine(MiningMachine machine);
    void removeMachine(MiningMachine machine);
    MiningMachine getMiningMachineAtPosition(int position_x, int position_y);
    Boolean isTargetWithinFieldLimits(int targetX, int targetY);
    Boolean isStepClear(Point stepStart, Point stepEnd);
}
