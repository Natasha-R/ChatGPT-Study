package thkoeln.st.st2praktikum.exercise.FieldComponent;

import thkoeln.st.st2praktikum.exercise.IField;

import java.util.UUID;

public class MiningMachine implements IMachine {

    private final String name;
    private IField field;
    private final UUID uuid;
    private int[] position = new int[]{0, 0};

    public MiningMachine(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public int[] getPosition() {
        return this.position;
    }

    @Override
    public void setField(IField field) {
        this.field = field;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public void setPosition(int xPosition, int yPosition) {
        this.position[0] = xPosition;
        this.position[1] = yPosition;
    }

    @Override
    public IField getField() {
        return field;
    }

    @Override
    public String getPositionAsString(){
        return "("+position[0]+","+position[1]+")";
    }

    @Override
    public boolean move(String direction, int length) {
        boolean success = false;
        switch(direction) {
            case "no":
                success = moveInSteps(length, 1, 0, 1);
                break;
            case "ea":
                success = moveInSteps(length, 1, 1, 0);
                break;
            case "so":
                success = moveInSteps(length, -1, 0, -1);
                break;
            case "we":
                success = moveInSteps(length, -1, -1, 0);
                break;
            default:
        }
        return success;
    }

    private boolean moveInSteps(int stepLength, int additionValue, int stepToX, int stepToY) {
        int positionIndex = 0;
        if (stepToY != 0) { positionIndex = 1; }
        for (int i = 0; i < Math.abs(stepLength); i++) {
            if (field.isStepClear(this.position[0], this.position[1], this.position[0] + stepToX, this.position[1] + stepToY)) {
                this.position[positionIndex] += additionValue;
            } else {
                return false;
            }
        }
        return true;
    }
}
