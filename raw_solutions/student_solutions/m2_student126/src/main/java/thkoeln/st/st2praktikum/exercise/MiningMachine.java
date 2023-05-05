package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Blocker;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Movable;

@NoArgsConstructor
@Getter
public class MiningMachine extends AbstractEntity implements Movable, Blocker {

    @Setter
    private String name;
    @Setter
    private Coordinate coordinate;

    public MiningMachine(String name) {
        this.name = name;
    }

    @Override
    public void move(CommandType direction) {
        switch (direction) {
            case NORTH:
                moveNorth();
                break;
            case EAST:
                moveEast();
                break;
            case SOUTH:
                moveSouth();
                break;
            case WEST:
                moveWest();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void moveNorth() {
        coordinate = new Coordinate(coordinate.getX(), coordinate.getY() + 1);
    }

    public void moveEast() {
        coordinate = new Coordinate(coordinate.getX() + 1, coordinate.getY());
    }

    public void moveSouth() {
        coordinate = new Coordinate(coordinate.getX(), coordinate.getY() - 1);
    }

    public void moveWest() {
        coordinate = new Coordinate(coordinate.getX() - 1, coordinate.getY());
    }

    @Override
    public boolean isBlocked(Coordinate current, Coordinate future) {
        return this.coordinate.equals(future);
    }
}
