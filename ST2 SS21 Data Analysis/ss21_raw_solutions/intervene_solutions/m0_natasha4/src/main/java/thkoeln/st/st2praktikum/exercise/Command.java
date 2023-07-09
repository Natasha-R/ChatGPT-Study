package thkoeln.st.st2praktikum.exercise;

public class Command {
    private String direction;
    private int steps;

    public Command(String direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    public String getDirection() {
        return direction;
    }

    public int getSteps() {
        return steps;
    }

    public static Command parseCommand(String commandString) {
        commandString = commandString.replaceAll("\\[|\\]", "");
        String[] parts = commandString.split(",");
        return new Command(parts[0], Integer.parseInt(parts[1]));
    }
}
