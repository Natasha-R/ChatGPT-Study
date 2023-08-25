package thkoeln.st.st2praktikum.exercise;

public class Command {
    Point from;
    Point to;
    Direction direction;

    public Command(Point from, Point to) {
        this.from = from;
        this.to = to;

        if (to.x > from.x) {
            direction = Direction.EAST;
        } else if (to.x < from.x) {
            direction = Direction.WEST;
        } else if (to.y > from.y) {
            direction = Direction.NORTH;
        } else if (to.y < from.y) {
            direction = Direction.SOUTH;
        } else {
            throw new RuntimeException("Not a valid move");
        }
    }
}
