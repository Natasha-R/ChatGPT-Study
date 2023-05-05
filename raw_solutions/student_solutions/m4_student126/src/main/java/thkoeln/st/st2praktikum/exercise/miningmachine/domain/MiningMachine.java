package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MiningMachine extends AbstractEntity implements Movable, Blockable {

    private String name;
    @Embedded
    private Coordinate coordinate;

    @ManyToOne
    private Field field;

    @ElementCollection
    private List<Command> commands = new ArrayList<>();

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

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void deleteCommandHistory() {
        commands.clear();
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

    public UUID getFieldId() {
        return field.getId();
    }

    @Override
    public boolean isBlocked(Coordinate current, Coordinate future) {
        return this.coordinate.equals(future);
    }
}
