package thkoeln.st.st2praktikum.exercise;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Moveable {

    final int DECK_WIDTH = 11;
    final int DECK_HEIGHT = 7;

    ArrayList<Barrier> barriers = new ArrayList<>();

    Point position;

    public Exercise0() {
        barriers.add(new Barrier(new Point(1, 4), new Point(8, 4)));
        barriers.add(new Barrier(new Point(4, 3), new Point(7, 3)));
        barriers.add(new Barrier(new Point(3, 0), new Point(3, 3)));
        barriers.add(new Barrier(new Point(7, 0), new Point(7, 2)));
        position = new Point(3, 0);
        System.out.println("reset position");
    }

    @Override
    public String moveTo(String moveCommandString) {
        Command command = parseCommandString(moveCommandString);
        ArrayList<Command> commands = getSingleSteps(command);
        Point endPos = null;
        for (Command c : commands) {
            for (Barrier b : barriers) {
                if (b.collides(c)) {
                    endPos = c.from;
                }
            }
        }
        if (endPos == null) endPos = command.to;
        position = endPos;
        return endPos.toString();
    }

    private Command parseCommandString(String moveCommandString) {
        Pattern p = Pattern.compile("\\[([a-z]{2}),([\\d]*)\\]");
        Matcher matcher = p.matcher(moveCommandString);

        Command c;
        if (matcher.matches()) {
            String directionString = matcher.group(1).toLowerCase();
            int length = Integer.parseInt(matcher.group(2));

            if (directionString.startsWith("n")) {
                c = new Command(position, new Point(position.x, position.y + length));
            } else if (directionString.startsWith("s")) {
                c = new Command(position, new Point(position.x, position.y - length));
            } else if (directionString.startsWith("e")) {
                c = new Command(position, new Point(position.x + length, position.y));
            } else if (directionString.startsWith("w")) {
                c = new Command(position, new Point(position.x - length, position.y));
            } else {
                throw new RuntimeException("Could not parse command direction: " + directionString);
            }
        } else {
            throw new RuntimeException("Could not parse commandString: " + moveCommandString);
        }

        if (c.to.x > DECK_WIDTH) {
            c.to.x = DECK_WIDTH;
        } else if (c.to.x < 0) {
            c.to.x = 0;
        }
        if (c.to.y > DECK_HEIGHT) {
            c.to.y = DECK_HEIGHT;
        } else if (c.to.y < 0) {
            c.to.y = 0;
        }

        return c;
    }

    private ArrayList<Command> getSingleSteps(Command c) {
        ArrayList<Command> commands = new ArrayList<>(11);
        switch (c.direction) {
            case NORTH:
                for (int i = c.from.y; i < c.to.y; i++) {
                    commands.add(new Command(new Point(c.from.x, i), new Point(c.to.x, i + 1)));
                }
                break;
            case SOUTH:
                for (int i = c.from.y; i >= c.to.y; i--) {
                    commands.add(new Command(new Point(c.from.x, i), new Point(c.from.x, i - 1)));
                }
                break;
            case EAST:
                for (int i = c.from.x; i < c.to.x; i++) {
                    commands.add(new Command(new Point(i, c.from.y), new Point(i + 1, c.to.y)));
                }
                break;
            case WEST:
                for (int i = c.from.x; i >= c.to.x; i--) {
                    commands.add(new Command(new Point(i, c.from.y), new Point(i - 1, c.from.y)));
                }
                break;
        }
        return commands;
    }
}
