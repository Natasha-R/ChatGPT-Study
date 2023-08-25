package thkoeln.st.st2praktikum.exercise;

import com.sun.istack.Nullable;

import java.util.Arrays;

public class Exercise0 implements Walkable {

    Coordinate robotPosition = new Coordinate(1, 7);
    Barrier[] barriers = {
            new Barrier(new Coordinate(0,0), new Coordinate(12, 0)),
            new Barrier(new Coordinate(0, 0), new Coordinate(0, 9)),
            new Barrier(new Coordinate(0, 9), new Coordinate(12, 9)),
            new Barrier(new Coordinate(12, 0), new Coordinate(12, 9)),
            new Barrier(new Coordinate(3, 3), new Coordinate(3, 9)),
            new Barrier(new Coordinate(3, 3), new Coordinate(5, 3)),
            new Barrier(new Coordinate(5, 0), new Coordinate(5, 2)),
            new Barrier(new Coordinate(6, 0), new Coordinate(6, 4))
    };

    public Barrier[] getHorizontalBarriers() {
       return Arrays.stream(this.barriers).filter(barrier -> barrier.start.y == barrier.end.y).toArray(size -> new Barrier[size]);
    }

    public Barrier[] getVerticalBarriers() {
        return Arrays.stream(this.barriers).filter(barrier -> barrier.start.x == barrier.end.x).toArray(size -> new Barrier[size]);
    }

    public enum Direction {
        no,
        ea,
        so,
        we
    }

    @Override
    public String walk(String walkCommandString) {
        String[] walkCommands = walkCommandString.replace("[", "").replace("]", "").split(",");
        Direction direction = Direction.valueOf(walkCommands[0]);
        int steps = Integer.parseInt(walkCommands[1]);

        Coordinate newPosition = new Coordinate(robotPosition.x, robotPosition.y);

        switch (direction) {
            case no:
                newPosition.y += steps;
                break;
            case ea:
                newPosition.x += steps;
                break;
            case so:
                newPosition.y -= steps;
                break;
            case we:
                newPosition.x -= steps;
                break;
        }

        Coordinate wallCoordinateBetween = coordinateOfBarrierBetween(robotPosition, newPosition);

        if(wallCoordinateBetween != null) {
            newPosition = wallCoordinateBetween;
            if (direction == Direction.ea) {
                newPosition.x--;
            } else if (direction == Direction.no) {
                newPosition.y--;
            }
        }

        this.robotPosition = newPosition;

        System.out.println("Set robot position to " + this.robotPosition.toString());
        return this.robotPosition.toString();
    }

    @Nullable
    public Coordinate coordinateOfBarrierBetween(Coordinate start, Coordinate end) {
        if(start.x == end.x) {
            // vertical movement
            if (start.y > end.y) {
                Coordinate tmp = start;
                start = end;
                end = tmp;
            }
            for(Barrier barrier: this.getHorizontalBarriers()) {
                if((barrier.start.y > start.y && barrier.start.y <= end.y)) {
                    // There is a barrier between start and end. Now we have to check if it is in the direct path
                    if((barrier.start.x <= start.x && barrier.end.x > start.x)) {
                        System.out.println("Something Between " + start.toString() + " and " + end.toString());
                        System.out.println("Wall coordinates: " + barrier.start.toString() + ", " + barrier.end.toString());
                        return new Coordinate(start.x, barrier.start.y);
                    }
                }
            }
        } else {
            // horizontal movement
            if (start.x > end.x) {
                Coordinate tmp = start;
                start = end;
                end = tmp;
            }
            for(Barrier barrier: this.getVerticalBarriers()) {
                if((barrier.start.x > start.x && barrier.start.x <= end.x)) {
                    // There is a barrier between start and end. Now we have to check if it is in the direct path
                    if((barrier.start.y <= start.y && barrier.end.y > start.y)) {
                        System.out.println("Something Between " + start.toString() + " and " + end.toString());
                        System.out.println("Wall coordinates: " + barrier.start.toString() + ", " + barrier.end.toString());
                        return new Coordinate(barrier.start.x, start.y);
                    }
                }
            }
        }

        return null;
    }
}
