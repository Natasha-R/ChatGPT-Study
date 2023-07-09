package thkoeln.st.st2praktikum.exercise;

public class Command {
    private final String direction;
    private final int steps;

    Command(String direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    static Command fromString(String str) {
        str = str.replace("[", "").replace("]", "");
        String[] parts = str.split(",");
        return new Command(parts[0], Integer.parseInt(parts[1]));
    }

    String getDirection() {
        return direction;
    }

    int getSteps() {
        return steps;
    }
}