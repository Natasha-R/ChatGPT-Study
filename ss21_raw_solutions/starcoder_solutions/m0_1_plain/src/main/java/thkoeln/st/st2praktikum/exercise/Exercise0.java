package koeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private static final char NORTH = 'N';
    private static final char EAST = 'E';
    private static final char SOUTH = 'S';
    private static final char WEST = 'W';

    @Override
    public String walk(final String walkCommandStr) {
        // Parse the walk command
        final Direction dir = parseDirectionFromWalkCmd(walkCommandStr);
        final Integer numSteps = parseIntFromWalkCmd(walkCommandStr);

        // Initialize variables
        Point currPos = null;
        boolean hitWall = true;
        Point nextPosition = null;

        while (!hitWall && numSteps > 0) {
            switch (dir) {
                case N -> currPos = moveNorth(currPos, numSteps);
                break;
                case E -> currPos = moveEast(currPoss, numSteps);
                break;
                case S -> currPos = moveSouth(currPos, numSteps);
                break;
                default -> currPos = moveWest(currPos, numSteps);
            }
            hitWall = !isValidPointOnField(currPos);
            nextPosition = getNextValidPositionAfterMove(currPos, dir, numSteps);
            numSteps--;
        }

        return "(" + (nextPosition != null ? (int) (nextPosition.getX()) : "") + ", " + (nextPosition != null ? (int) (nextPosition.getY()) : "") + ")";
    }

    /**
     * Parses the direction part of the walk command
     */
    protected Direction parseDirectionFromWalkCmd(final String cmd) {
        Character c = cmd != null && cmd.length() >= 2 ? cmd.charAt(1) : ' ';
        switch (Character.toUpperCase(c)) {
            case 'N':
                return Direction.NORTH;
            case 'E':
                return Direction.EAST;
            case 'S':
                return Direction.SOUTH;
            case 'W':
                return Direction.WEST;
            default:
                throw new IllegalArgumentException("Invalid Command");
        }
    }

    /**
     * Parses the numeric part of the walk command
     */
    protected Integer parseIntFromWalkCmd(final String cmd) {
        StringBuilder sb = new StringBuilder();
        for (char ch : cmd.toCharArray()) {
            if ((ch < '0' || ch > '9') && !(ch == ',' || ch == ' '))
                continue;
            sb.append(ch);
        }
        return Integer.parseInt(sb.toString());
    }

    /* Moves northwards */
    protected Point moveNorth(final Point pos, Integer numSteps) {
        return new Point(pos.getX(), Math.max(-1, (int) (pos.getY() - numSteps)));
    }

    /* Moves eastwards */
    protected Point moveEast(final Point pos, Integer numSteps) {
        return new Point(Math.min(((int) (pos.getX() + numSteps)), FIELDSIZE), (int) (pos.getY()));
    }

    /* Moves southwards */
    protected Point moveSouth(final Point pos, Integer numSteps) {
        return new Point(pos.getX(), Math.min((((int) (pos.getY() + numSteps))), FIELDSIZE));
    }

    /* Moves westwards */
    protected Point moveWest(final Point pos, Integer numSteps) {
        return new Point(Math.max((-1 * (int) ((pos.getX() - numSteps)))), ((int) (pos.getY())));
    }

    /* Returns whether point lies outside bounds of field */
    protected Boolean isValidPointOnField(final Point pt) {
        return ((pt.getX() >= -1 && (pt.getX() <= (FIELDSIZE))) && (pt.getY() >= -1 && (pt.getY() <= FIELDSIZE)));
    }

    /* Returns valid position after movement */
    protected Point getNextValidPositionAfterMove(final Point pos, final Direction dir, final Integer numSteps) {
        Point nextPt = null;
        switch (dir) {
            case N -> nextPt = moveNorth(pos, numSteps);
            break;
            case E -> nextPt = moveEast(pos, numSteps);
            break;
            case S -> nextPt = moveSouth(pos, numSteps);
            break;
            default -> nextPt = moveWest(pos, numSteps);
        }
        return (!isValidPointOnField(nextPt) ? getNextValidPositionAfterMove(nextPt, dir, --numSteps) : nextPt);
    }

    enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    ;

}
