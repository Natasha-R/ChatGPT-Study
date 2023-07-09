package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {

    private Position currentPosition = new Position(3, 0);

    private static final int MAX_X = 11;
    private static final int MAX_Y = 7;

    @Override
    public String walkTo(String walkCommandString) {

        Command command = parseCommand(walkCommandString);

        for (int i = 0; i < command.getSteps(); i++) {

            if (canMove(command.getDirection())) {
                currentPosition.move(command.getDirection());
            } else {

                break;
            }
        }

        return currentPosition.toString();
    }

    private Command parseCommand(String walkCommandString) {

        Pattern pattern = Pattern.compile("\\[(\\w+),(\\d+)\\]");
        Matcher matcher = pattern.matcher(walkCommandString);

        if (matcher.find()) {
            String direction = matcher.group(1);
            int steps = Integer.parseInt(matcher.group(2));
            return new Command(direction, steps);
        } else {

            throw new IllegalArgumentException("Invalid command format");
        }
    }

    private boolean canMove(String direction) {

        return currentPosition.canMove(direction);
    }

    private static class Position {

        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move(String direction) {

            switch (direction) {

                case "no":
                    this.y++;
                    break;
                case "ea":
                    this.x++;
                    break;
                case "so":
                    this.y--;
                    break;
                case "we":
                    this.x--;
                    break;

                default:
                    throw new IllegalArgumentException("Invalid direction");
            }
        }

        public boolean canMove(String direction) {

            switch (direction) {

                case "no": // north
                    return !(this.y == MAX_Y || (this.x == 3 && this.y == 2)
                            || (this.x >= 4 && this.x <= 7 && this.y == 2)
                            || (this.x >= 1 && this.x <= 8 && this.y == 3));
                case "ea": // east
                    return !(this.x == MAX_X || (this.x == 2 && this.y >= 0 && this.y <= 3)
                            || (this.x == 6 && this.y >= 0 && this.y <= 2));
                case "so": // south
                    return !(this.y == 0 || (this.x == 3 && this.y == 4)
                            || (this.x >= 4 && this.x <= 7 && this.y == 4)
                            || (this.x >= 1 && this.x <= 8 && this.y == 5));
                case "we": // west
                    return !(this.x == 0 || (this.x == 4 && this.y >= 0 && this.y <= 3)
                            || (this.x == 8 && this.y >= 0 && this.y <= 2));
                default:
                    throw new IllegalArgumentException("Invalid direction");
            }
        }

        @Override
        public String toString() {

            return String.format("(%d,%d)", this.x, this.y);
        }
    }

    private static class Command {

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
    }
}

