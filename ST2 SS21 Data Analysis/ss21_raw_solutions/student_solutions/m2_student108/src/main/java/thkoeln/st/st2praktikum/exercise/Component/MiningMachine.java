package thkoeln.st.st2praktikum.exercise.Component;

import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.UUID;

public class MiningMachine implements IComponentWithUUID {
    String name;
    Field field;
    UUID uuid;
    Point position = new Point(0, 0);

    public MiningMachine(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public void setField(Field field) {
        this.field = field;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setPosition(Point position) {
        this.position = new Point(position.getX(), position.getY());
    }

    public Field getField() {
        return field;
    }

    public Point getPosition() {
        return this.position;
    }

    public boolean move(OrderType direction, int length) {
        boolean success = false;
        switch(direction) {
            case NORTH:
                success = moveInSteps(length, 1, 0, 1);
                break;
            case EAST:
                success = moveInSteps(length, 1, 1, 0);
                break;
            case SOUTH:
                success = moveInSteps(length, -1, 0, -1);
                break;
            case WEST:
                success = moveInSteps(length, -1, -1, 0);
                break;
            default:
        }
        return success;
    }

    private boolean moveInSteps(int stepLength, int toAdd, int stepToX, int stepToY) {
        if (this.field == null) return false;
        Boolean changeXVariable = true;
        if (stepToY != 0) { changeXVariable = false; }
        if (stepLength < 0) {toAdd = toAdd * -1; }
        for (int i = 0; i < Math.abs(stepLength); i++) {
            if (this.position.getX() + stepToX >= 0 && this.position.getY() + stepToY >= 0 &&
                    field.isStepClear(this.position, new Point(this.position.getX() + stepToX, this.position.getY() + stepToY))) {
                if (changeXVariable)
                    this.position = new Point(this.position.getX()+toAdd, this.position.getY());
                else
                    this.position = new Point(this.position.getX(), this.position.getY()+toAdd);
            } else {
                return false;
            }
        }
        return true;
    }
}
