package thkoeln.st.st2praktikum.exercise;

class Command {

    private String direction;
    private int steps;

    public static Command parse(String commandString) throws IllegalArgumentException {
        commandString = commandString.substring(1, commandString.length()-1);
        String[] parts = commandString.split(",");
        return new Command(parts[0], Integer.parseInt(parts[1]));
    }

    public Command(String direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    public void execute(Robot robot) {
        switch (direction) {
            case "no":
                robot.moveNorth(steps);
                break;
            case "ea":
                robot.moveEast(steps);
                break;
            case "so":
                robot.moveSouth(steps);
                break;
            case "we":
                robot.moveWest(steps);
                break;
        }
    }
}
