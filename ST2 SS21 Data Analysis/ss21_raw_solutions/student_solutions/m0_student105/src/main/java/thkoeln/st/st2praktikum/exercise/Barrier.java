package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    Point p1;
    Point p2;
    Direction direction;

    public Barrier(Point p1, Point p2) {
        if (p1.x == p2.x) {
            this.direction = Direction.NORTH;
        } else {
            this.direction = Direction.EAST;
        }
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean collides(Command c) {
        Direction moveDirection = c.direction;

        // Cannot collide if moving parallel to the barrier
        if (isParallel(direction, moveDirection)) {
            return false;
        }

        switch (moveDirection) {
            case EAST: if (c.to.x == p1.x && c.to.y >= p1.y && c.to.y < p2.y) return true;           break;
            case WEST: if (c.from.x == p1.x && c.to.y >= p1.y && c.to.y < p2.y) return true;         break;
            case NORTH: if (c.to.y == p1.y && c.to.x >= p1.x && c.to.x < p2.x) return true;          break;
            case SOUTH: if (c.from.y == p1.y && c.to.x >= p1.x && c.to.x < p2.x) return true;        break;
        }

        return false;
    }

    private boolean isParallel(Direction barrierDirection, Direction moveDirection) {
        if (barrierDirection == Direction.EAST && ( moveDirection == Direction.EAST ||moveDirection == Direction.WEST))
            return true;
        else return barrierDirection == Direction.NORTH && (moveDirection == Direction.NORTH || moveDirection == Direction.SOUTH);
    }

}
