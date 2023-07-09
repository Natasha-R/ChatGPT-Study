package thkoeln.st.st2praktikum.exercise.entities;

import thkoeln.st.st2praktikum.exercise.OrderType;

public class Move {
    private final Coordinate start;
    private OrderType direction;

    public Move(Coordinate start, OrderType direction) {
        this.start = start;
        this.direction = direction;
    }

    public Move(Coordinate start, Coordinate destination) {
        this.start = start;
        if (!(start.getX() == destination.getX())) {
            switch (destination.getX() - start.getX()) {
                case 1:
                    this.direction = OrderType.NORTH;
                    break;
                case -1:
                    this.direction = OrderType.SOUTH;
                    break;
            }
        } else if (!(start.getY() == destination.getY())) {
            switch (destination.getX() - start.getX()) {
                case 1:
                    this.direction = OrderType.EAST;
                    break;
                case -1:
                    this.direction = OrderType.WEST;
                    break;
            }
        }
    }


    public Coordinate getStart() {
        return start;
    }

    public OrderType getDirection() {
        return direction;
    }


    public Move reverse() {
        OrderType oppositeDirection;
        int x = start.getX();
        int y = start.getY();

        switch (direction) {
            case NORTH:
                y += 1;
                oppositeDirection = OrderType.SOUTH;
                break;
            case SOUTH:
                y -= 1;
                oppositeDirection = OrderType.NORTH;
                break;
            case EAST:
                x += 1;
                oppositeDirection = OrderType.WEST;
                break;
            case WEST:
                x -= 1;
                oppositeDirection = OrderType.EAST;
                break;
            default:
                throw new IllegalArgumentException("Invalid Direction in Move:" + this);
        }

        return new Move(new Coordinate(x, y), oppositeDirection);
    }

    public Coordinate destination() {
        int x = start.getX();
        int y = start.getY();

        switch (direction) {
            case NORTH:
                y += 1;
                break;
            case SOUTH:
                y -= 1;
                break;
            case EAST:
                x += 1;
                break;
            case WEST:
                x -= 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid Direction in Move:" + this);
        }
        return new Coordinate(x, y);
    }

    public boolean equals(Object o) {
        if (o instanceof Move) {
            Move move = (Move) o;
            return this.getStart().equals(move.getStart()) && this.getDirection().equals(move.getDirection());
        } else return false;
    }
}
