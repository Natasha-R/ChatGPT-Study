package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachine {
    private final UUID miningMachineId;
    private final String machineName;
    private Position currentPosition = null;
    private Field currentField = null;
    private MachineDeploymentState state = MachineDeploymentState.HIBERNATING;

    public MiningMachine(String machineName) {
        this.miningMachineId = UUID.randomUUID();
        this.machineName = machineName;
    }

    public String getMachineName() {
        return this.machineName;
    }

    public Position getPosition() {
        return this.currentPosition;
    }

    public void setPosition(int x, int y) {
        this.currentPosition = new Position(x, y);
    }

    public void setPosition(Position p){
        this.currentPosition = p;
    }

    public Field getCurrentField() {
        return this.currentField;
    }

    public void setField(Field field) {
        this.currentField = field;
    }

    public MachineDeploymentState getState() {
        return this.state;
    }

    public void setState(MachineDeploymentState s) {
        this.state = s;
    }

    public String getPositionString() {
        return "(" + currentPosition.getX() + "," + currentPosition.getY() + ")";
    }

    public UUID getId() {
        return this.miningMachineId;
    }
}