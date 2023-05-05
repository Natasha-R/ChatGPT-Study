package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movement {

    private static final String PATTERN = "\\[(\\w{2})\\,(\\d+)\\]";

    private Direction direction;
    private int moves = 0;

    public Movement(String movement) {
        parseMovement(movement);
    }

    public Movement(Direction direction, int moves) {
        this.direction = direction;
        this.moves = moves;
    }

    public Position makeStep(Position position) {
        return makeStep(position, 1);
    }

    public Position makeStep(Position position, int desiredMoves) {
        Position previewSteps = previewSteps(position, desiredMoves);
        this.moves -= desiredMoves > this.moves ? this.moves : desiredMoves;
        return previewSteps;
    }

    public Position previewStep(Position position) {
        return previewSteps(position, 1);
    }

    public Position previewSteps(Position position, int desiredMoves) {
        int actualMoves = desiredMoves;
        if (desiredMoves > this.moves) {
            actualMoves = this.moves;
        }
        return new Position(position.getX() + (actualMoves * direction.getXFactor()), position.getY() + (actualMoves * direction.getYFactor()));
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return "[" + direction.getCode() +
                "," + moves +
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return moves == movement.moves && direction == movement.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, moves);
    }

    private void parseMovement(String movement) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(movement);

        if (matcher.find()) {
            direction = Direction.getValue(matcher.group(1));
            moves = Integer.parseInt(matcher.group(2));
        }
    }
}
