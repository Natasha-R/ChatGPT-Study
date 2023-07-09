package thkoeln.st.st2praktikum.exercise.command;

public class WalkCommand extends Command {
    private final Direction direction;
    private final int steps;

    WalkCommand(Direction direction, int steps) {
        super(CommandType.walk);
        this.direction = direction;
        this.steps = steps;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSteps() {
        return steps;
    }
}
