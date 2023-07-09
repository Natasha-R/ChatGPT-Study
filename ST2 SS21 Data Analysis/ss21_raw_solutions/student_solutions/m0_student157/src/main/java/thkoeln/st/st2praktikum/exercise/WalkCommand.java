package thkoeln.st.st2praktikum.exercise;

public class WalkCommand {

    public Direction direction;
    public int steps;

    WalkCommand(String walkCommandString) {
        setDirection(walkCommandString);
        setSteps(walkCommandString);
    }

    private void setSteps(String walkCommandString) {
        char[] stepsArray = new char[walkCommandString.length()-5];
        walkCommandString.getChars(4, walkCommandString.length() -1, stepsArray, 0);
        String stepsString = new String(stepsArray);
        this.steps = Integer.parseInt(stepsString);
    }

    private void setDirection(String walkCommandString) {
        String directionString = getString(walkCommandString);
        setDirectionEnum(directionString);
    }

    private void setDirectionEnum(String directionString) {
        switch (directionString) {
            case "no":
                this.direction = Direction.NORTH;
                break;
            case "ea":
                this.direction = Direction.EAST;
                break;
            case "so":
                this.direction = Direction.SOUTH;
                break;
            case "we":
                this.direction = Direction.WEST;
                break;
        }
    }

    private String getString(String walkCommandString) {
        return String.valueOf(walkCommandString.charAt(1)) + walkCommandString.charAt(2);
    }

    public void print() {
        System.out.println(direction);
        System.out.println(steps);
    }
}
