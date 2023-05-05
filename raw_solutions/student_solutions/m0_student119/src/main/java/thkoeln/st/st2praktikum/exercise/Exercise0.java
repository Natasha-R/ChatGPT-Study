package thkoeln.st.st2praktikum.exercise;

import java.awt.*;
import java.util.List;

public class Exercise0 implements Walkable {

    // Barriers
    // (0,6) - (2,6)
    // (3,6) - (3,8)
    // (1,5) - (9,5)
    // (9,1) - (9,5)
    private final List<Barrier> mBarriers = List.of(
            // Map borders
            new Barrier(new Point(0, 0), new Point(11, 0), BarrierType.horizontal),
            new Barrier(new Point(0, 8), new Point(11, 8), BarrierType.horizontal),
            new Barrier(new Point(0, 0), new Point(0, 8), BarrierType.vertical),
            new Barrier(new Point(11, 0), new Point(11, 8), BarrierType.vertical),

            // Barriers
            new Barrier(new Point(0, 6), new Point(2, 6), BarrierType.horizontal),
            new Barrier(new Point(3, 6), new Point(3, 8), BarrierType.vertical),
            new Barrier(new Point(1, 5), new Point(9, 5), BarrierType.horizontal),
            new Barrier(new Point(9, 1), new Point(9, 5), BarrierType.vertical)
    );

    // Start
    // (1,6)
    private final TilePosition mCurrentPosition = new TilePosition(
            new Point(1, 6),
            new Point(1, 7),
            new Point(2, 7),
            new Point(2, 6)
    );

    @Override
    public String walkTo(String walkCommandString) {
        // 1. in which direction are we moving?
        // 2. get barriers for this direction
        // 3. get barriers for current tile position, which barrier would be crossed?
        // 4. move to the new tile position
        // 5. return new tile position (bottom left corner)
        final WalkCommand command = parseCommand(walkCommandString);

        for (int i = 0; i < command.steps; i++) {
            // if the border coordinate is lower than the barrier limit, we can move
            final boolean limitReached = barrierReached(command);
            if(limitReached)
                break;

            mCurrentPosition.walk(command.direction, 1);
        }
        return mCurrentPosition.getCoordinates();
    }

    private boolean barrierReached(WalkCommand command) {
        final int border = mCurrentPosition.getBorder(command.direction);
        return mBarriers.stream().anyMatch(barrier -> {
            switch (command.direction) {
                // we are interested in barriers where the y coordinate is greater than or equal
                // the current tile position, and the x coordinate is in rage of barriers x coordinates
                case north:
                case south:
                    return barrier.type == BarrierType.horizontal &&
                            mCurrentPosition.leftTop.x >= barrier.start.x && mCurrentPosition.rightTop.x <= barrier.end.x &&
                            border == barrier.start.y;
                case east:
                case west:
                    final boolean barrierTypeMatch = barrier.type == BarrierType.vertical;
                    final boolean barrierInRange = mCurrentPosition.leftBottom.y >= barrier.start.y && mCurrentPosition.leftTop.y <= barrier.end.y;
                    return barrierTypeMatch &&
                            barrierInRange &&
                            border == barrier.start.x;
                default:
                    throw new UnsupportedOperationException();
            }
        });
    }

    private WalkCommand parseCommand(String commandString) {
        var replaced = commandString.replace("]", "");
        replaced = replaced.replace("[", "");
        var splits = replaced.split(",");
        var directionString = splits[0];
        var steps = splits[1];

        Direction direction;
        switch (directionString) {
            case "no":
                direction = Direction.north;
                break;
            case "ea":
                direction = Direction.east;
                break;
            case "so":
                direction = Direction.south;
                break;
            case "we":
                direction = Direction.west;
                break;
            default:
                throw new UnsupportedOperationException();

        }

        return new WalkCommand(direction, Integer.parseInt(steps));
    }

    private static class TilePosition {
        final public Point leftBottom;
        final public Point leftTop;
        final public Point rightTop;
        final public Point rightBottom;

        private TilePosition(Point leftBottom, Point leftTop, Point rightTop, Point rightBottom) {
            this.leftBottom = leftBottom;
            this.leftTop = leftTop;
            this.rightTop = rightTop;
            this.rightBottom = rightBottom;
        }

        private void walk(Direction direction, int steps) {
            var stepPoint = getStepPoint(direction, steps);
            updatePoint(leftBottom, stepPoint);
            updatePoint(leftTop, stepPoint);
            updatePoint(rightTop, stepPoint);
            updatePoint(rightBottom, stepPoint);
        }

        private void updatePoint(Point point, Point updates) {
            point.move(point.x + updates.x, point.y + updates.y);
        }

        private Point getStepPoint(Direction direction, int steps) {
            switch (direction) {
                case north:
                    return new Point(0, steps);
                case east:
                    return new Point(steps, 0);
                case south:
                    return new Point(0, -steps);
                case west:
                    return new Point(-steps, 0);
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public int getBorder(Direction direction) {
            switch (direction) {
                case north:
                    return leftTop.y;
                case east:
                    return rightTop.x;
                case south:
                    return rightBottom.y;
                case west:
                    return leftTop.x;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public String getCoordinates() {
            return "(" + leftBottom.x + "," + leftBottom.y + ")";
        }
    }

    private static class WalkCommand {
        final Direction direction;
        final int steps;

        private WalkCommand(Direction direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }
    }

    private enum Direction {
        north, east, south, west
    }

    private static class Barrier {
        public final Point start;
        public final Point end;
        public final BarrierType type;

        private Barrier(Point start, Point end, BarrierType type) {
            this.start = start;
            this.end = end;
            this.type = type;
        }

        public int getLimit() {
            switch (type) {
                case horizontal:
                    return start.y;
                case vertical:
                    return start.x;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    private enum BarrierType {
        horizontal, vertical
    }
}
