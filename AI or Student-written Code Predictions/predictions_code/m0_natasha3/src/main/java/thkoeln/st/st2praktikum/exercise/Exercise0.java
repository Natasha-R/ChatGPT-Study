package thkoeln.st.st2praktikum.exercise;
import java.util.*;

public class Exercise0 implements Moveable {
    private Position position;
    private Set<Barrier> barriers;

    public Exercise0() {
        this.position = new Position(7, 7);

        this.barriers = new HashSet<>();
        barriers.add(new Barrier(new Position(2, 1), new Position(10, 1)));
        barriers.add(new Barrier(new Position(2, 1), new Position(2, 6)));
        barriers.add(new Barrier(new Position(2, 6), new Position(7, 6)));
        barriers.add(new Barrier(new Position(10, 1), new Position(10, 8)));
    }

    public String move(String moveCommandString) {
        String[] command = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = command[0];
        int steps = Integer.parseInt(command[1]);

        if (direction.equals("no")) {
            for (int i = 0; i < steps; i++) {
                Position nextPosition = new Position(position.x, position.y + 1);
                if (nextPosition.y < 8 && !barrierInPath(position, nextPosition, "no")) {
                    position = nextPosition;
                }
            }
        } else if (direction.equals("ea")) {
            for (int i = 0; i < steps; i++) {
                Position nextPosition = new Position(position.x + 1, position.y);
                if (nextPosition.x < 11 && !barrierInPath(position, nextPosition, "ea")) {
                    position = nextPosition;
                }
            }
        } else if (direction.equals("so")) {
            for (int i = 0; i < steps; i++) {
                Position nextPosition = new Position(position.x, position.y - 1);
                if (nextPosition.y >= 0 && !barrierInPath(position, nextPosition, "so")) {
                    position = nextPosition;
                }
            }
        } else if (direction.equals("we")) {
            for (int i = 0; i < steps; i++) {
                Position nextPosition = new Position(position.x - 1, position.y);
                if (nextPosition.x >= 0 && !barrierInPath(position, nextPosition, "we")) {
                    position = nextPosition;
                }
            }
        }

        return position.toString();
    }

    private boolean barrierInPath(Position start, Position end, String direction) {
        for (Barrier barrier : barriers) {
            if (direction.equals("no") || direction.equals("so")) {
                if (barrier.orientation.equals("horizontal") && barrier.intersects(start, end, direction)) {
                    return true;
                }
            } else {
                if (barrier.orientation.equals("vertical") && barrier.intersects(start, end, direction)) {
                    return true;
                }
            }
        }
        return false;
    }

}
