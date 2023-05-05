package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    int[] position;
    Field field;
    public Exercise0() {
        this.position = new int[]{8,3};
        this.field = new Field(12, 9, 4);
        field.addWall(new Wall(3, 1, 3, 7));
        field.addWall(new Wall(6, 1, 9, 1));
        field.addWall(new Wall(5, 2, 5, 5));
        field.addWall(new Wall(6, 4, 9, 4));
    }

    public void moveViaDirection(String direction, int length) {
        switch(direction) {
            case "no":
                moveInSteps(length, 1, 0, 1);
                break;
            case "ea":
                moveInSteps(length, 1, 1, 0);
                break;
            case "so":
                moveInSteps(length, -1, 0, -1);
                break;
            case "we":
                moveInSteps(length, -1, -1, 0);
                break;
            default:
        }
    }

    public boolean moveInSteps(int stepLength, int toAdd, int stepToX, int stepToY) {
        int positionIndex = 0;
        if (stepToY != 0) { positionIndex = 1; }
        if (stepLength < 0) {toAdd = toAdd * -1; }
        for (int i = 0; i < Math.abs(stepLength); i++) {
            if (field.isStepClear(this.position[0], this.position[1], this.position[0] + stepToX, this.position[1] + stepToY)) {
                this.position[positionIndex] += toAdd;
            } else {
                return false;
            }
        }
        return true;
    }

    public String[] splitToArgs(String moveCommandString) {
        String s = moveCommandString.substring(1, moveCommandString.length()-1);
        String[] arr = s.split(",");
        return arr;
    }

    @Override
    public String move(String moveCommandString) {
        String[] arr = splitToArgs(moveCommandString);
        moveViaDirection(arr[0], Integer.parseInt(arr[1]));
        return "(" + this.position[0] + "," + this.position[1] + ")";
    }
}
