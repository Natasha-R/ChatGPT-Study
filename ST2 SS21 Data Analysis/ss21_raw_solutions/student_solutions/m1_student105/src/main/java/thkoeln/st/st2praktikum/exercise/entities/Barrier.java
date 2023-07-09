package thkoeln.st.st2praktikum.exercise.entities;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Direction;
import thkoeln.st.st2praktikum.exercise.entities.commands.MoveCommand;
import thkoeln.st.st2praktikum.exercise.interfaces.Blocker;

import javax.persistence.Embeddable;


@Embeddable
@NoArgsConstructor
public class Barrier implements Blocker {

    Coordinate p1;
    Coordinate p2;
    Direction direction;

    public Barrier(Coordinate p1, Coordinate p2) {
        if (p1.x == p2.x) {
            this.direction = Direction.NORTH;
        } else {
            this.direction = Direction.EAST;
        }
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean blocks(MoveCommand c) {
        Direction moveDirection = c.direction;

        // Cannot collide if moving parallel to the barrier
        if (isParallel(direction, moveDirection)) {
            return false;
        }

        switch (moveDirection) {
            case EAST: if (c.endPosition.x == p1.x && c.endPosition.y >= p1.y && c.endPosition.y < p2.y) return true;           break;
            case WEST: if (c.startPosition.x == p1.x && c.endPosition.y >= p1.y && c.endPosition.y < p2.y) return true;         break;
            case NORTH: if (c.endPosition.y == p1.y && c.endPosition.x >= p1.x && c.endPosition.x < p2.x) return true;          break;
            case SOUTH: if (c.startPosition.y == p1.y && c.endPosition.x >= p1.x && c.endPosition.x < p2.x) return true;        break;
        }

        return false;
    }

    private boolean isParallel(Direction barrierDirection, Direction moveDirection) {
        if (barrierDirection == Direction.EAST && ( moveDirection == Direction.EAST ||moveDirection == Direction.WEST))
            return true;
        else return barrierDirection == Direction.NORTH && (moveDirection == Direction.NORTH || moveDirection == Direction.SOUTH);
    }

}
